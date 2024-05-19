package uz.etc.etcfitness.user;

import org.springframework.stereotype.Service;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.entity.UserEntity;

@Service
public class UserMapper {

    public UserDto toUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .phone(userEntity.getPhone())
                .status(userEntity.getStatus())
                .roles(userEntity.getRoles())
                .telegramId(userEntity.getTelegramId())
                .gender(userEntity.getGender())
                .profilePicture(userEntity.getProfilePicture())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())

                .build();
    }

}
