package hhplus.tdd.concert.application.service.concert;

import hhplus.tdd.concert.app.application.service.concert.ConcertService;
import hhplus.tdd.concert.application.service.TestBase;
import hhplus.tdd.concert.app.domain.entity.waiting.Waiting;
import hhplus.tdd.concert.app.domain.repository.concert.ConcertSeatRepository;
import hhplus.tdd.concert.app.domain.repository.member.MemberRepository;
import hhplus.tdd.concert.app.domain.repository.waiting.WaitingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConcertServiceIntegrationTest {

    private final TestBase testBase = new TestBase();

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private WaitingRepository waitingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 동시성_2명이_1개_좌석_예약() throws InterruptedException {
        // given
        int capacity = 1;
        AtomicInteger failCnt = new AtomicInteger();
        List<Waiting> waitingList = List.of(testBase.waitingActive, testBase.waitingActive2); // 대기열 리스트 생성
        int totalTasks = waitingList.size();
        memberRepository.save(testBase.member);
        memberRepository.save(testBase.member2);
        waitingRepository.save(testBase.waitingActive);
        waitingRepository.save(testBase.waitingActive2);
        concertSeatRepository.save(testBase.concertSeatStandBy);

        CountDownLatch latch = new CountDownLatch(totalTasks);
        ExecutorService executorService = Executors.newFixedThreadPool(totalTasks);

        // when
        for (Waiting waiting : waitingList) {
            executorService.execute(() -> {
                try {
                    concertService.processReserve(waiting.getToken(), testBase.concertSeatStandBy.getSeatId());
                } catch (Exception e) {
                    if (e.getMessage().equals("이미 임시배정된 좌석입니다.")) failCnt.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        assertEquals(totalTasks - capacity, failCnt.get());

        executorService.shutdown();
    }
}