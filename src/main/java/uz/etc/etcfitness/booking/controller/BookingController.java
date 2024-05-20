package uz.etc.etcfitness.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.etc.etcfitness.booking.dto.BookingDto;
import uz.etc.etcfitness.booking.dto.request.BookingCreateRequest;
import uz.etc.etcfitness.booking.service.BookingService;
import uz.etc.etcfitness.common.PageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingDto> book(@Valid @RequestBody BookingCreateRequest booking) {
        return ResponseEntity.ok(bookingService.book(booking));
    }

    @GetMapping("/get")
    public ResponseEntity<BookingDto> getBookingById(@RequestParam Long bookingId, Authentication connectedUser) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId,connectedUser));
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<BookingDto>> getAllBookings(@RequestParam int page, @RequestParam int size,
                                                                   Authentication connectedUser) {
        return ResponseEntity.ok(bookingService.getAll(page, size, connectedUser));
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<PageResponse<BookingDto>> getBookingsByUser(@RequestParam int page, @RequestParam int size, @RequestParam Long userId) {
        return ResponseEntity.ok(bookingService.getAllBookingsByUserId(page, size, userId));
    }
    @GetMapping("/available-slots")
    public List<LocalDateTime> getAvailableSlots(@RequestParam LocalDate date) {
        return bookingService.getAvailableSlots(date);
    }


}
