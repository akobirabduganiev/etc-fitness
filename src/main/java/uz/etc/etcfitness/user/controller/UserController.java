package uz.etc.etcfitness.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.enums.UserStatus;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all-waiting-status-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAllWatingStatusUsers(int page, int size) {
        return ResponseEntity.ok(userService.getAllWatingStatusUsers(page, size));
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAllUsers(int page, int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @GetMapping("/get-all-users-by-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAllUsersByStatus(int page, int size, UserStatus status) {
        return ResponseEntity.ok(userService.getAllUsersByStatus(page, size, status));
    }

    @GetMapping("/get-user-by-id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
