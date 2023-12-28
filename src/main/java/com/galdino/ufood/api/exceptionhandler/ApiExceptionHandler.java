package com.galdino.ufood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.galdino.ufood.core.validation.ValidationException;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.Reference;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String SYSTEM_ERROR_MSG = "System error occurred. Try again. If no success, contact the system administrator.";

    private MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UEntityNotFoundException.class)
    public ResponseEntity<?> handleUEntityNotFoundException(UEntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage())
                          .userMessage(ex.getMessage())
                          .build();

//        Problem problem = Problem.builder()
//                .status(status.value())
//                .type("https://ufood.com/entity-not-found")
//                .title("Entity not found")
//                .detail(ex.getMessage())
//                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        Problem problem = createProblemBuilder(status, ProblemType.ENTITY_IN_USE, ex.getMessage())
                          .userMessage(ex.getMessage())
                          .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = createProblemBuilder(status, ProblemType.BUSINESS_ERROR, ex.getMessage())
                          .userMessage(ex.getMessage())
                          .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) throws MethodArgumentNotValidException {
        return handleMethodArgumentNotValid(new MethodArgumentNotValidException(null, ex.getBindingResult()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "The request's body is invalid.";
        Problem problem = createProblemBuilder(status, ProblemType.NOT_UNDERSTANDABLE_MESSAGE, detail)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = getPath(ex);

        String detail = String.format("The property '%s' has an invalid type. Expected type is %s.", path, ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.NOT_UNDERSTANDABLE_MESSAGE, detail)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = getPath(ex);
        String detail = String.format("The property '%s' is invalid.", path);

        Problem problem = createProblemBuilder(status, ProblemType.NOT_UNDERSTANDABLE_MESSAGE, detail)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    private static String getPath(JsonMappingException ex) {
        String path = ex.getPath().stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
        return path;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("The URL's parameter '%s' has the value '%s', it's invalid type. The correct type is %s.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.INVALID_PARAMETER, detail)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("The resource '%s' does not exist.", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "One or more fields are invalid.";

        List<Problem.Object> problemObjects = ex.getBindingResult().getAllErrors().stream()
                                                                                  .map(objectError -> {
                                                                                      String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                                                                                      String name = objectError.getObjectName();

                                                                                      if (objectError instanceof FieldError) {
                                                                                          name = ((FieldError) objectError).getField();
                                                                                      }

                                                                                      return Problem.Object.builder()
                                                                                            .name(name)
                                                                                            .userMessage(message)
                                                                                            .build();
                                                                                      })
                                                                                  .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, ProblemType.INVALID_FIELD, detail)
                          .userMessage(detail)
                          .objects(problemObjects)
                          .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, ProblemType.SYSTEM_ERROR, SYSTEM_ERROR_MSG)
                          .userMessage(SYSTEM_ERROR_MSG)
                          .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(SYSTEM_ERROR_MSG)
                    .time(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .time(LocalDateTime.now());
    }
}
