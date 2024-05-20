package uz.etc.etcfitness.user;

import org.springframework.stereotype.Service;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.entity.User;

@Service
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .status(user.getStatus())
                .roles(user.getRoles())
                .telegramId(user.getTelegramId())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())

                .build();
    }

}
