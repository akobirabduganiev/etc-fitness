package uz.etc.etcfitness.booking.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateRequest {
    private Long userId;
    private LocalDateTime startTime;
}
