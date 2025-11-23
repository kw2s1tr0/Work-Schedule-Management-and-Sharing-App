package com.schedule.app.handler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.schedule.app.exception.DomainException;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * DomainException を統一的に返す
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(DomainException ex) {
        HttpStatus status;
        ErrorResponse body;
        switch (ex.getError()) {
            case VALIDATION_ERROR:
                status = HttpStatus.BAD_REQUEST;
                body = new ErrorResponse(
                    List.of(ex.getMessage()),
                    LocalDateTime.now());
                break;
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                body = new ErrorResponse(
                    List.of(ex.getMessage()),
                    LocalDateTime.now());
                break;
            case CONFLICT:
                status = HttpStatus.CONFLICT;
                body = new ErrorResponse(
                    List.of(ex.getMessage()),
                    LocalDateTime.now());
                break;
            default:
                status = HttpStatus.I_AM_A_TEAPOT; // 418 I'm a teapot
                body = new ErrorResponse(
                    List.of("unknown error"),
                    LocalDateTime.now());
                break;
        }

        return ResponseEntity.status(status).body(body);
    }

    /**
     * バリデーションエラー（@Valid 用）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        List<String> message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        ErrorResponse body = new ErrorResponse(
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * 想定外の例外（NullPointerException など）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                List.of("Internal server error"),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // エラーレスポンス用の内部クラス
    public static class ErrorResponse {
        private final List<String> message;
        private final LocalDateTime timestamp;

        public ErrorResponse(List<String> message, LocalDateTime timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }

        public List<String> getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}