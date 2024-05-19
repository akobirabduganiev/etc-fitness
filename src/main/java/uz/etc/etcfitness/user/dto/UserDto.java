package uz.etc.etcfitness.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import uz.etc.etcfitness.enums.Gender;
import uz.etc.etcfitness.enums.UserStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.etc.etcfitness.user.entity.UserEntity}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record UserDto(Long id, String firstname, String lastname, String phone, String password, Long telegramId,
                      Gender gender, String profilePicture, UserStatus status, LocalDateTime createdAt,
                      LocalDateTime updatedAt) implements Serializable {
}