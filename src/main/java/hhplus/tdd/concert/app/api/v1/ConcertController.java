package hhplus.tdd.concert.app.api.v1;

import hhplus.tdd.concert.app.api.dto.response.ErrorRes;
import hhplus.tdd.concert.app.api.dto.response.concert.ConcertScheduleRes;
import hhplus.tdd.concert.app.api.dto.response.concert.ConcertSeatRes;
import hhplus.tdd.concert.app.application.dto.concert.ConcertScheduleQuery;
import hhplus.tdd.concert.app.application.dto.concert.ConcertSeatQuery;
import hhplus.tdd.concert.app.application.service.concert.ConcertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "콘서트 API", description = "모든 API 는 대기열 토큰 값이 필요합니다.")
@RestController
@RequestMapping("/api/v1/concert")
@RequiredArgsConstructor
@Slf4j
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/date")
    @Operation(summary = "예약 가능 날짜 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConcertScheduleRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
    })
    public ResponseEntity<List<ConcertScheduleRes>> getConcertDate(
            @Parameter(hidden = true) @RequestHeader("waitingToken") String waitingToken
    ){
        List<ConcertScheduleQuery> restResponse = concertService.loadConcertDate(waitingToken);
        return new ResponseEntity<>(restResponse.stream()
                .map(ConcertScheduleRes::from)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}/seat")
    @Operation(summary = "예약 가능 좌석 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConcertSeatRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
    })
    public ResponseEntity<List<ConcertSeatRes>> getConcertSeat(
            @Parameter(hidden = true) @RequestHeader("waitingToken") String waitingToken,
            @Schema(description = "콘서트 스케줄 ID")
            @PathVariable("scheduleId") long scheduleId
    ){
        List<ConcertSeatQuery> restResponse = concertService.loadConcertSeat(waitingToken, scheduleId);
        return new ResponseEntity<>(restResponse.stream()
                .map(ConcertSeatRes::from)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

}
