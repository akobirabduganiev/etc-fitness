package uz.etc.etcfitness.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.etc.etcfitness.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramId(Long telegramId);

    Optional<UserEntity> findByPhone(String username);
}