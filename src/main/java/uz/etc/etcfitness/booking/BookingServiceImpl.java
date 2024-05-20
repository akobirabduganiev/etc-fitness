package uz.etc.etcfitness.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.etc.etcfitness.booking.dto.request.BookingCreateRequest;
import uz.etc.etcfitness.booking.entity.Booking;
import uz.etc.etcfitness.booking.repository.BookingRepository;
import uz.etc.etcfitness.common.ResponseMessage;
import uz.etc.etcfitness.enums.BookingStatus;
import uz.etc.etcfitness.user.entity.User;
import uz.etc.etcfitness.user.repository.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final int MAX_USERS_PER_SLOT = 10;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    public Booking createBooking(BookingCreateRequest booking) {
        LocalDateTime endTime = booking.getStartTime().plusMinutes(90);
        if (booking.getStartTime().getHour() < 9 || endTime.getHour() >= 19 || booking.getStartTime().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Invalid booking time");
        }

        List<Booking> bookings = bookingRepository.findBookingsInTimeRange(booking.getStartTime(), endTime);
        if (bookings.size() >= MAX_USERS_PER_SLOT) {
            throw new IllegalArgumentException("Slot is full");
        }
        var user = userRepository.findById(booking.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var newBooking = new Booking();
        newBooking.setUser(user);
        newBooking.setStartTime(booking.getStartTime());
        newBooking.setEndTime(endTime);
        newBooking.setStatus(BookingStatus.ACCEPTED);

        bookingRepository.save(newBooking);
        return newBooking;
    }


    @Override
    public Booking findBookingById(Long bookingId) {
        return null;
    }

    @Override
    public ResponseMessage changeBookingStatus(Long bookingId, BookingStatus status) {
        return null;
    }

    @Override
    public List<Booking> findBookingsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return List.of();
    }

    @Override
    public List<Booking> findBookingsByUser(Long userId) {
        return List.of();
    }

    @Override
    public void deleteBooking(Long bookingId) {

    }
}
