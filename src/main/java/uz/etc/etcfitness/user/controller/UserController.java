package uz.etc.etcfitness.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.user.dto.UserDto;
import uz.etc.etcfitness.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all-waiting-status-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAllWatingStatusUsers(int page, int size, Authentication connectedUser) {
        return ResponseEntity.ok(userService.getAllWatingStatusUsers(page, size, connectedUser));
    }
}
