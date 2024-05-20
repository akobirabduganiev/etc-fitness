package uz.etc.etcfitness.booking.service;

import org.springframework.security.core.Authentication;
import uz.etc.etcfitness.booking.dto.BookingDto;
import uz.etc.etcfitness.booking.dto.request.BookingCreateRequest;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.common.ResponseMessage;
import uz.etc.etcfitness.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingDto book(BookingCreateRequest booking);

    BookingDto getBookingById(Long bookingId, Authentication connectedUser);

    ResponseMessage changeBookingStatus(Long bookingId, BookingStatus status);

    PageResponse<BookingDto> getAll(int page, int size, Authentication connectedUser);

    PageResponse<BookingDto> getAllBookingsByUserId(int page, int size, Long userId);

    List<BookingDto> findBookingsInTimeRange(LocalDateTime startTime, LocalDateTime endTime);


    List<LocalDateTime> getAvailableSlots(LocalDate date);
}
