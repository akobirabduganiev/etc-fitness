package uz.etc.etcfitness.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import uz.etc.etcfitness.enums.Gender;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.role.Role;
import uz.etc.etcfitness.user.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link User}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record UserDto(Long id, String firstname, String lastname, String phone, String password, Long telegramId,
                      Gender gender, String profilePicture, UserStatus status, List<Role> roles, LocalDateTime createdAt,
                      LocalDateTime updatedAt) implements Serializable {
}