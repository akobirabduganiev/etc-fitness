package uz.etc.etcfitness.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.exception.ItemNotFoundException;
import uz.etc.etcfitness.user.UserMapper;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.entity.User;
import uz.etc.etcfitness.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public PageResponse<UserDto> getAllWatingStatusUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<User> users = userRepository.findAllByStatus(pageable, UserStatus.WAITING);
        List<UserDto> usersResponse = users.stream()
                .map(userMapper::toUserDto)
                .toList();
        return new PageResponse<>(
                usersResponse,
                users.getNumber() + 1,
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
    }

    @Override
    public PageResponse<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<User> users = userRepository.findAll(pageable);
        List<UserDto> usersResponse = users.stream()
                .map(userMapper::toUserDto)
                .toList();
        return new PageResponse<>(
                usersResponse,
                users.getNumber() + 1,
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    @Override
    public PageResponse<UserDto> getAllUsersByStatus(int page, int size, UserStatus status) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<User> users = userRepository.findAllByStatus(pageable, status);
        List<UserDto> usersResponse = users.stream()
                .map(userMapper::toUserDto)
                .toList();
        return new PageResponse<>(
                usersResponse,
                users.getNumber() + 1,
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

}
