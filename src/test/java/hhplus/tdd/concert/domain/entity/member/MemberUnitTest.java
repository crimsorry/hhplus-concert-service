package hhplus.tdd.concert.domain.entity.member;

import hhplus.tdd.concert.domain.exception.ErrorCode;
import hhplus.tdd.concert.domain.exception.FailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberUnitTest {

    @Test
    public void 존재_하지_않는_맴버(){
        // given
        Member member = null;

        // when & then
        Exception exception = assertThrows(FailException.class, () -> {
            Member.checkMemberExistence(member);
        });

        // 결과 검증
        assertEquals(ErrorCode.NOT_FOUNT_MEMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void 한도_초과(){
        // given
        int amount = 6000000;
        Member member = new Member(1L, "김소리", 5000000);

        // when & then
        Exception exception = assertThrows(FailException.class, () -> {
            Member.checkMemberCharge(member, amount);
        });

        // 결과 검증
        assertEquals(ErrorCode.FULL_PAY.getMessage(), exception.getMessage());
    }

    @Test
    public void 잔액_부족(){
        // given
        int amount = 4000;
        Member member = new Member(1L, "김소리", 300);

        // when & then
        Exception exception = assertThrows(FailException.class, () -> {
            Member.checkMemberChargeLess(member, amount);
        });

        // 결과 검증
        assertEquals(ErrorCode.EMPTY_PAY.getMessage(), exception.getMessage());
    }

}
