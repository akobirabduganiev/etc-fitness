package uz.etc.etcfitness.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.etc.etcfitness.booking.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.startTime BETWEEN :startTime AND :endTime AND b.status = 'ACCEPTED'")
    List<Booking> findBookingsInTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Page<Booking> findBookingsByUserId(Long userId, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Page<Booking> findAllByUserId(Long userId, Pageable pageable);
}