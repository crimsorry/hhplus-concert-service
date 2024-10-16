package hhplus.tdd.concert.application.service.waiting;

import hhplus.tdd.concert.application.dto.waiting.QueueTokenDto;
import hhplus.tdd.concert.application.service.WaitingService;
import hhplus.tdd.concert.domain.entity.member.Member;
import hhplus.tdd.concert.domain.entity.waiting.Waiting;
import hhplus.tdd.concert.domain.entity.waiting.WaitingStatus;
import hhplus.tdd.concert.domain.repository.member.MemberRepository;
import hhplus.tdd.concert.domain.repository.waiting.WaitingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WaitingServiceTest {

    @InjectMocks
    private WaitingService waitingService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private WaitingRepository waitingRepository;

    @Test
    public void 유저_대기열_생성_성공() {
        // given
        long memberId = 1L;
        Member member = new Member(memberId, "김소리", 0);
        Waiting waiting = null; // 대기열이 없는 상태를 가정

        // when
        when(memberRepository.findByMemberId(memberId)).thenReturn(member);
        when(waitingRepository.findByMemberAndStatusNot(member, WaitingStatus.EXPIRED))
                .thenReturn(waiting); // 대기열이 없을 때 null 반환
        when(waitingRepository.save(any(Waiting.class))).thenAnswer(invocation -> {
            Waiting savedQueue = invocation.getArgument(0);
            savedQueue.setWaitingId(1L); // save 후에 ID가 생성됨을 가정
            return savedQueue;
        });

        // then
        QueueTokenDto result = waitingService.enqueueMember(memberId);

        // 결과 검증
        assertNotNull(result);
        assertNotNull(result.queueToken());
        verify(memberRepository).findByMemberId(memberId);
        verify(waitingRepository).findByMemberAndStatusNot(member, WaitingStatus.EXPIRED);
        verify(waitingRepository).save(any(Waiting.class));
    }

    @Test
    public void 유저_대기열_순서_조회() {
        // given
        String queueToken = "testToken";

        // when
        long result = waitingService.loadWaiting(queueToken).num();

        // then
        assertEquals(1, result);
    }
}
