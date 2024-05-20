package uz.etc.etcfitness.booking.dto;

import uz.etc.etcfitness.enums.BookingStatus;
import uz.etc.etcfitness.user.dto.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.etc.etcfitness.booking.entity.Booking}
 */
public record BookingDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId,
                         LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) implements Serializable {
}