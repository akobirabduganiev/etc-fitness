package uz.etc.etcfitness.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.etc.etcfitness.enums.RoleName;
import uz.etc.etcfitness.role.Role;
import uz.etc.etcfitness.role.RoleRepository;
import uz.etc.etcfitness.user.User;
import uz.etc.etcfitness.user.UserRepository;

import java.util.List;

@Component
public class SetupDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (roleRepository.findAll().isEmpty()) {
            var admin = new Role();
            admin.setName(RoleName.ADMIN);
            roleRepository.save(admin);

            var superAdmin = new Role();
            superAdmin.setName(RoleName.SUPER_ADMIN);
            roleRepository.save(superAdmin);

            var user = new Role();
            user.setName(RoleName.USER);
            roleRepository.save(user);
        }

        if (userRepository.findAll().isEmpty()) {
            // fetch the admin Role from the DB
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin Role not found"));
            // create an Admin User and set the password
            var adminUser = new User();
            adminUser.setFirstname("Admin");
            adminUser.setLastname("Admin");
            adminUser.setPhone("998932158000");
            adminUser.setPassword(passwordEncoder.encode("admin_password")); // use encoded password
            adminUser.setRoles(List.of(adminRole));
            userRepository.save(adminUser);
        }
    }
}
