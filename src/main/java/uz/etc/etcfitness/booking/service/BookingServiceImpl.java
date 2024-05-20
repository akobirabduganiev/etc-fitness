package uz.etc.etcfitness.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.etc.etcfitness.booking.dto.BookingDto;
import uz.etc.etcfitness.booking.dto.request.BookingCreateRequest;
import uz.etc.etcfitness.booking.entity.Booking;
import uz.etc.etcfitness.booking.mapper.BookingMapper;
import uz.etc.etcfitness.booking.repository.BookingRepository;
import uz.etc.etcfitness.common.PageResponse;
import uz.etc.etcfitness.common.ResponseMessage;
import uz.etc.etcfitness.enums.BookingStatus;
import uz.etc.etcfitness.enums.RoleName;
import uz.etc.etcfitness.exception.ItemNotFoundException;
import uz.etc.etcfitness.user.entity.User;
import uz.etc.etcfitness.user.repository.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final int MAX_USERS_PER_SLOT = 10;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDto book(BookingCreateRequest booking) {
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
        return bookingMapper.toBookingDto(newBooking);
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ItemNotFoundException("Booking not found"));

        if (!user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ADMIN)) && !booking.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public PageResponse<BookingDto> getAll(int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        User user = ((User) connectedUser.getPrincipal());
        Page<Booking> bookings;

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ADMIN))) {
            bookings = bookingRepository.findAll(pageable);
        } else {
            bookings = bookingRepository.findAllByUserId(user.getId(), pageable);
        }

        return new PageResponse<>(
                bookingMapper.toBookingDtoList(bookings.getContent()),
                bookings.getNumber() + 1,
                bookings.getSize(),
                bookings.getTotalElements(),
                bookings.getTotalPages(),
                bookings.isFirst(),
                bookings.isLast()
        );
    }


    @Override
    public ResponseMessage changeBookingStatus(Long bookingId, BookingStatus status) {
        var booking = bookingRepository.findById(bookingId).
                orElseThrow(() -> new ItemNotFoundException("Booking not found"));
        booking.setStatus(status);
        bookingRepository.save(booking);
        return new ResponseMessage("Booking status changed");
    }


    @Override
    public List<BookingDto> findBookingsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingMapper.toBookingDtoList(bookingRepository.findBookingsInTimeRange(startTime, endTime));
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(LocalDate date) {
        LocalDateTime startOfDay = date.atTime(9, 0);
        LocalDateTime endOfDay = date.atTime(19, 0);

        List<LocalDateTime> availableSlots = new ArrayList<>();

        for (LocalDateTime time = startOfDay; time.isBefore(endOfDay); time = time.plusMinutes(90)) {
            LocalDateTime slotEndTime = time.plusMinutes(90);
            List<Booking> bookings = bookingRepository.findBookingsInTimeRange(time, slotEndTime);
            if (bookings.size() < MAX_USERS_PER_SLOT) {
                availableSlots.add(time);
            }
        }

        return availableSlots;
    }

    @Override
    public PageResponse<BookingDto> getAllBookingsByUserId(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        var bookings = bookingRepository.findBookingsByUserId(userId, pageable);
        return new PageResponse<>(
                bookingMapper.toBookingDtoList(bookings.getContent()),
                bookings.getNumber() + 1,
                bookings.getSize(),
                bookings.getTotalElements(),
                bookings.getTotalPages(),
                bookings.isFirst(),
                bookings.isLast()
        );

    }

}
