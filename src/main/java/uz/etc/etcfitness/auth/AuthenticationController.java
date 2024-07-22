package uz.etc.etcfitness.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.etc.etcfitness.booking.dto.SlotInfo;
import uz.etc.etcfitness.booking.service.BookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final BookingService bookingService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/auth/available-slots")
    public List<SlotInfo> getAvailableSlots(@RequestParam Optional<LocalDate> date) {
        LocalDate dateValue = date.orElse(LocalDate.now().plusDays(3));
        return bookingService.getAvailableSlots(dateValue);
    }



}
