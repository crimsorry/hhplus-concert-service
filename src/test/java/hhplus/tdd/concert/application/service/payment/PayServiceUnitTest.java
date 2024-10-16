package hhplus.tdd.concert.application.service.payment;

import hhplus.tdd.concert.application.dto.concert.ReservationDto;
import hhplus.tdd.concert.application.dto.concert.SReserveStatus;
import hhplus.tdd.concert.application.dto.payment.LoadAmountDto;
import hhplus.tdd.concert.application.dto.payment.UpdateChargeDto;
import hhplus.tdd.concert.application.service.TestBase;
import hhplus.tdd.concert.domain.entity.payment.AmountHistory;
import hhplus.tdd.concert.domain.repository.payment.AmountHistoryRepository;
import hhplus.tdd.concert.domain.repository.payment.PaymentRepository;
import hhplus.tdd.concert.domain.repository.waiting.WaitingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayServiceUnitTest extends TestBase {

    @InjectMocks
    private PayService payService;

    @Mock
    private WaitingRepository waitingRepository;

    @Mock
    private AmountHistoryRepository amountHistoryRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void 잔액_충전_성공() {
        // when
        when(waitingRepository.findByToken(eq(waitingToken))).thenReturn(waiting);
        when(amountHistoryRepository.save(any(AmountHistory.class))).thenAnswer(invocation -> {
            AmountHistory amountHistory = invocation.getArgument(0);
            amountHistory.setPointId(1L); // save 후에 ID가 생성됨을 가정
            return amountHistory;
        });
        // then
        UpdateChargeDto result = payService.chargeAmount(waitingToken, amount);

        // 결과검증
        assertNotNull(result);
        verify(waitingRepository).findByToken(waitingToken);
        verify(amountHistoryRepository).save(any(AmountHistory.class));
        assertEquals(true, result.isCharge());
    }

    @Test
    public void 잔액_조회() {
        // when
        when(waitingRepository.findByToken(eq(waitingToken))).thenReturn(waiting);

        // then
        LoadAmountDto result = payService.loadAmount(waitingToken);

        // 결과검증
        assertEquals(member.getCharge(), result.amount());
    }

    @Test
    public void 결제_처리_성공() {
        // when
        when(waitingRepository.findByToken(eq(waitingToken))).thenReturn(waiting);
        when(paymentRepository.findByPayId(eq(1L))).thenReturn(payment);

        // then
        ReservationDto result = payService.processPay(waitingToken, 1L);

        // 결과검증
        assertEquals(title, result.concertTitle());
        assertEquals(SReserveStatus.RESERVED, result.reserveStatus());
    }

}