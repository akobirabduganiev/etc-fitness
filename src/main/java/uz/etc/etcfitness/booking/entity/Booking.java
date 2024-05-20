package uz.etc.etcfitness.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.etc.etcfitness.common.BaseEntity;
import uz.etc.etcfitness.enums.BookingStatus;
import uz.etc.etcfitness.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}