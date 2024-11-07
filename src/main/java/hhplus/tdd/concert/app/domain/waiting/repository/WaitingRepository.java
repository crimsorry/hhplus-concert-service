package hhplus.tdd.concert.app.domain.waiting.repository;

import hhplus.tdd.concert.app.domain.waiting.entity.Waiting;
import hhplus.tdd.concert.app.domain.waiting.model.ActiveToken;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

public interface WaitingRepository {

    public void addWaitingToken(String key, Object value, Long currentTime);

    public Long getWaitingTokenScore(String key, Object value);

    public Boolean isWaitingTokenKey(String key);

    public Set<Object> getWaitingTokenRange(String key, int start, int end);

    public void deleteWaitingToken(String key, Object value);

    public List<ActiveToken> getActiveToken(String key);

    public ZSetOperations<String, Object> getWaitingToken();

    public Set<Object> getAllTokens(String tokenKey);

    public Boolean isActiveToken(String key, long memberId);

    public void deleteActiveToken(String key, Object value);

    public void addActiveToken(String key, Object value);

    default Waiting findByTokenOrThrow(String waitigToken){
        return null;
    }


}