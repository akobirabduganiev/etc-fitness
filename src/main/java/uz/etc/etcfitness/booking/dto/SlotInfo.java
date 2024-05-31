package uz.etc.etcfitness.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotInfo {
    private LocalDateTime startTime;
    private int numberOfUsers;
    private List<BookingDto> bookings;

}
