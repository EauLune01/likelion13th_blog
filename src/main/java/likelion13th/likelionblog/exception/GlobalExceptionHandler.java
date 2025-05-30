package likelion13th.likelionblog.exception;

import jakarta.persistence.EntityNotFoundException;
import likelion13th.likelionblog.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException e){

        return buildErrorResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ApiResponse> handlePermissionDeniedException(PermissionDeniedException ex){
        return buildErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage());
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleArticleNotFoundException(ArticleNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCommentNotFoundException(CommentNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    private static ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status,String message) {
        ApiResponse response=new ApiResponse(false,status.value(),message);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}