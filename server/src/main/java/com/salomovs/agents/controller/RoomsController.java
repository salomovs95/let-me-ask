package com.salomovs.agents.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.agents.dto.AnswerQuestionDto;
import com.salomovs.agents.dto.CreateQuestionDto;
import com.salomovs.agents.dto.CreateRoomDto;
import com.salomovs.agents.dto.RoomResponse;
import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsController {
  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<List<RoomResponse>> getRooms() {
    List<RoomResponse> rooms = roomService.listRooms()
                                  .stream()
                                  .map(RoomResponse::parse)
                                  .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }

  @PostMapping
  public ResponseEntity<Void> openRoom(@RequestBody CreateRoomDto body) {
    String roomId = roomService.createRoom(body);
    log.warn("New Room Opened: " + roomId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{room_slug}")
  public ResponseEntity<RoomResponse> findRoomBySlug(@PathVariable(name="room_slug") String roomSlug) {
    Room room = roomService.findRoom(roomSlug);
    RoomResponse res = RoomResponse.parse(room);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @PostMapping("/{room_slug}/questions")
  public ResponseEntity<Void> placeQuestion(@PathVariable(name="room_slug") String roomSlug, @RequestBody CreateQuestionDto body) {
    String questionId = roomService.createQuestion(roomSlug, body);
    log.warn("New Question Placed: " + questionId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{room_slug}/questions/{question_id}")
  public ResponseEntity<Void> answerQuestion(@PathVariable(name="room_slug") String roomSlug, @PathVariable(name="question_id") String questionId, @RequestBody @Valid AnswerQuestionDto body) {
    log.warn("Updating question " + questionId + " from room " + roomSlug);
    roomService.updateQuestionAnswer(questionId, body.answer());
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
