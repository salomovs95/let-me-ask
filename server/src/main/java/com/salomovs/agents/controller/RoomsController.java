package com.salomovs.agents.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.agents.dto.CreateQuestionDto;
import com.salomovs.agents.dto.CreateRoomDto;
import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsController {
  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<List<Room>> getRooms() {
    List<Room> rooms = roomService.listRooms();
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }

  @PostMapping
  public ResponseEntity<Void> openRoom(@RequestBody CreateRoomDto body) {
    String roomId = roomService.createRoom(body);
    log.warn("New Room Opened: " + roomId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/{id}/questions")
  public ResponseEntity<Void> placeQuestion(@RequestParam String slug, @RequestBody CreateQuestionDto body) {
    String questionId = roomService.createQuestion(slug, body);
    log.warn("New Question Placed: " + questionId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
