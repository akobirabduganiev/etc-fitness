package uz.etc.etcfitness.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramId(Long telegramId);

    Optional<UserEntity> findByPhone(String username);

    @Query("select u from UserEntity u where u.status = :userStatus")
    Page<UserEntity> findAllByStatus(Pageable pageable, UserStatus userStatus);
}