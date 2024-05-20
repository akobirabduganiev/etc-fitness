package uz.etc.etcfitness.booking.mapper;

import org.springframework.stereotype.Service;
import uz.etc.etcfitness.booking.dto.BookingDto;
import uz.etc.etcfitness.booking.entity.Booking;

import java.util.List;

@Service
public class BookingMapper {
    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getCreatedAt(), booking.getUpdatedAt(), booking.getUser().getId(),
                booking.getStartTime(), booking.getEndTime(), booking.getStatus());
    }

    public List<BookingDto> toBookingDtoList(List<Booking> bookingsInTimeRange) {
        return bookingsInTimeRange.stream().map(this::toBookingDto).toList();
    }
}
