package uz.etc.etcfitness.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.etc.etcfitness.bot.config.TelegramBotConfig;
import uz.etc.etcfitness.enums.RoleName;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.exception.ItemNotFoundException;
import uz.etc.etcfitness.role.RoleRepository;
import uz.etc.etcfitness.security.JwtService;
import uz.etc.etcfitness.user.UserEntity;
import uz.etc.etcfitness.user.UserRepository;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final TelegramBotConfig telegramBotConfig;

    @Transactional
    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

        var user = userRepository.findById(request.getId())
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        userRepository.findByPhone(request.getPhone())
                .ifPresent(u -> {
                    if (!u.getId().equals(request.getId())) {
                        throw new IllegalStateException("Phone number already exists");
                    }
                });

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        var generatedPassword = passwordGenerator();
        user.setPassword(passwordEncoder.encode(generatedPassword));
        user.setRoles(List.of(userRole));
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setStatus(UserStatus.ACTIVE);


        userRepository.save(user);
        sendGeneratedPassword(user.getTelegramId(), generatedPassword);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((UserEntity) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (UserEntity) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String passwordGenerator() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (characters.length() * Math.random());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    private void sendGeneratedPassword(Long telegramId, String password) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(telegramId);
        sendMessage.setText("Your password: " + password);
        telegramBotConfig.sendMsg(sendMessage);
    }

}
