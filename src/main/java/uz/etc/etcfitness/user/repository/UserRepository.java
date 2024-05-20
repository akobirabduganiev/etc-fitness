package uz.etc.etcfitness.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(Long telegramId);

    Optional<User> findByPhone(String username);

    @Query("select u from User u where u.status = :userStatus")
    Page<User> findAllByStatus(Pageable pageable, UserStatus userStatus);
}