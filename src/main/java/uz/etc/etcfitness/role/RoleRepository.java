package uz.etc.etcfitness.role;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.etc.etcfitness.enums.RoleName;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
