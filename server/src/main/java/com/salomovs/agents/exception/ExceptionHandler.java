package com.salomovs.agents.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handle(Exception e) {
    log.error(e.getLocalizedMessage());
    Map<String, String> res = new HashMap<>();
    res.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<Map<String, String>> handle(DataNotFoundException e) {
    log.error(e.getLocalizedMessage());
    Map<String, String> res = new HashMap<>();
    res.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AudioChunkProcessingException.class)
  public ResponseEntity<Map<String, String>> handle(AudioChunkProcessingException e) {
    log.error(e.getLocalizedMessage());
    Map<String, String> res = new HashMap<>();
    res.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
}
