package hhplus.tdd.concert.app.application.service.reservation;

import hhplus.tdd.concert.app.application.payment.dto.PayDTO;
import hhplus.tdd.concert.app.application.reservation.dto.ReservationDTO;
import hhplus.tdd.concert.app.application.reservation.service.ReservationService;
import hhplus.tdd.concert.app.application.service.TestBase;
import hhplus.tdd.concert.app.domain.concert.repository.ConcertSeatRepository;
import hhplus.tdd.concert.app.domain.payment.entity.Payment;
import hhplus.tdd.concert.app.domain.payment.repository.PaymentRepository;
import hhplus.tdd.concert.app.domain.reservation.entity.Reservation;
import hhplus.tdd.concert.app.domain.reservation.repository.ReservationRepository;
import hhplus.tdd.concert.app.domain.waiting.repository.WaitingRepository;
import hhplus.tdd.concert.config.types.ReserveStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationUnitTest {

    private final TestBase testBase = new TestBase();

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private WaitingRepository waitingRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void 좌석_예약() {
        // when
        when(concertSeatRepository.findBySeatIdWithPessimisticLock(testBase.concertSeatStandBy.getSeatId())).thenReturn(testBase.concertSeatStandBy);
        when(waitingRepository.findByTokenOrThrow(testBase.waitingToken).get()).thenReturn(testBase.activeToken);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation reservation = invocation.getArgument(0);
            return reservation.builder()
                    .reserveId(1L)
                    .reserveStatus(ReserveStatus.RESERVED)
                    .build();
        });
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            return payment.builder()
                    .payId(1L)
                    .isPay(true)
                    .build();
        });

        // then
        PayDTO result = reservationService.processReserve(testBase.waitingToken, testBase.concertSeatStandBy.getSeatId());

        // 결과검증
        assertEquals(true, result.isPay());
    }

    @Test
    public void 좌석_예약_리스트() {
        // when
        when(waitingRepository.findByTokenOrThrow(testBase.waitingToken).get()).thenReturn(testBase.activeToken);
        when(reservationRepository.findByMember(testBase.member)).thenReturn(testBase.reservations);

        // then
        List<ReservationDTO> result = reservationService.loadReservation(testBase.waitingToken);

        // 결과검증
        assertEquals(1, result.size());
        assertEquals(testBase.concert.getConcertTitle(), result.get(0).concertTitle());
        assertEquals(testBase.reservations.get(0).getAmount(), result.get(0).amount());
    }

}