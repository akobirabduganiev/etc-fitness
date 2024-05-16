package uz.etc.etcfitness.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.etc.etcfitness.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return repository.findByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
