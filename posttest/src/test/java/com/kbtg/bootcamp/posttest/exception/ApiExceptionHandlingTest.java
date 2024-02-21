package com.kbtg.bootcamp.posttest.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionHandlingTest {

    @Test
    @DisplayName("Handle validation exceptions")
    void testHandleValidationExceptions() {
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);
        FieldError mockFieldError = new FieldError("testObjectName", "testField", "Test error message");

        when(mockBindingResult.getFieldErrors()).thenReturn(java.util.Collections.singletonList(mockFieldError));
        when(mockException.getBindingResult()).thenReturn(mockBindingResult);

        ApiExceptionHandling apiExceptionHandling = new ApiExceptionHandling();
        ResponseEntity<Map<String, String>> responseEntity = apiExceptionHandling.handleValidationExceptions(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<String, String> responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertEquals(1, responseBody.size());
        assertEquals("Test error message", responseBody.get("testField"));
    }
}
