package uz.etc.etcfitness.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.user.UserMapper;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.entity.UserEntity;
import uz.etc.etcfitness.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public PageResponse<UserDto> getAllWatingStatusUsers(int page, int size, Authentication connectedUser) {
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<UserEntity> users = userRepository.findAll(pageable);
        List<UserDto> usersResponse = users.stream()
                .map(userMapper::toUserDto)
                .toList();
        return new PageResponse<>(
                usersResponse,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

}
