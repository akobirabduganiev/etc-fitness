package uz.etc.etcfitness.booking;

import uz.etc.etcfitness.booking.dto.request.BookingCreateRequest;
import uz.etc.etcfitness.booking.entity.Booking;
import uz.etc.etcfitness.common.ResponseMessage;
import uz.etc.etcfitness.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking createBooking(BookingCreateRequest booking);

    Booking findBookingById(Long bookingId);

    ResponseMessage changeBookingStatus(Long bookingId, BookingStatus status);

    List<Booking> findBookingsInTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    List<Booking> findBookingsByUser(Long userId);

    void deleteBooking(Long bookingId);
}
