package uz.etc.etcfitness.user.service;

import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.user.dto.UserDto;

public interface UserService {
    PageResponse<UserDto> getAllWatingStatusUsers(int page, int size);

    PageResponse<UserDto> getAllUsers(int page, int size);

    PageResponse<UserDto> getAllUsersByStatus(int page, int size, UserStatus status);

    UserDto getUserById(Long id);

}
