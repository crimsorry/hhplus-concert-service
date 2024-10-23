package hhplus.tdd.concert.app.api.v1.reservation;

import hhplus.tdd.concert.app.api.dto.response.ErrorRes;
import hhplus.tdd.concert.app.api.dto.response.reservation.ReservationRes;
import hhplus.tdd.concert.app.api.dto.response.payment.PayRes;
import hhplus.tdd.concert.app.application.dto.payment.PayDto;
import hhplus.tdd.concert.app.application.dto.reservation.ReservationDto;
import hhplus.tdd.concert.app.application.service.reservation.ReservationService;
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

@Tag(name = "예약 API", description = "모든 API 는 대기열 토큰 값이 필요합니다.")
@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{seatId}/reserve")
    @Operation(summary = "좌석 예약 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PayRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
    })
    public ResponseEntity<PayRes> createConcertReserve(
            @Parameter(hidden = true) @RequestHeader("waitingToken") String waitingToken,
            @Schema(description = "콘서트 좌석 ID")
            @PathVariable("seatId") long seatId
    ){
        PayDto restResponse = reservationService.processReserve(waitingToken, seatId);
        return new ResponseEntity<>(PayRes.from(restResponse), HttpStatus.OK);
    }

    @GetMapping("/reserves")
    @Operation(summary = "유저 예약 내역")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PayRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRes.class))),
    })
    public ResponseEntity<List<ReservationRes>> getReservation(
            @Parameter(hidden = true) @RequestHeader("waitingToken") String waitingToken
    ){
        List<ReservationDto> restResponse = reservationService.loadReservation(waitingToken);
        return new ResponseEntity<>(ReservationRes.from(restResponse), HttpStatus.OK);
    }

}
