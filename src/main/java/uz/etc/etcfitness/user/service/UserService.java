package uz.etc.etcfitness.user.service;

import org.springframework.security.core.Authentication;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.user.dto.UserDto;

public interface UserService {
    PageResponse<UserDto> getAllWatingStatusUsers(int page, int size, Authentication connectedUser);
}
