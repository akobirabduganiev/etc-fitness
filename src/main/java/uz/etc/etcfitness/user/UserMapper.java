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
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())

                .build();
    }

    public UserEntity toUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.id())
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .phone(userDto.phone())
                .status(userDto.status())
                .createdAt(userDto.createdAt())
                .updatedAt(userDto.updatedAt())
                .build();
    }
}
