package uz.etc.etcfitness.booking.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateRequest {
    @NotNull(message = "User id must not be null")
    private Long userId;
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;
}
