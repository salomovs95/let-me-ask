package com.salomovs.agents.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salomovs.agents.dto.AnswerQuestionDto;
import com.salomovs.agents.dto.CreateQuestionDto;
import com.salomovs.agents.dto.CreateRoomDto;
import com.salomovs.agents.dto.RoomResponse;
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
  public ResponseEntity<AnswerQuestionDto> placeQuestion(@PathVariable(name="room_slug") String roomSlug, @RequestBody CreateQuestionDto body) {
    AnswerQuestionDto response = roomService.createQuestion(roomSlug, body);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/{room_slug}/audio")
  public ResponseEntity<String> uploadAudio(@PathVariable(name="room_slug") String roomSlug, @RequestParam("file") MultipartFile audioFile) {
    Room room = roomService.findRoom(roomSlug);
    String chunkId = roomService.handleUploadAudio(room.getId(), audioFile);

    log.warn("Uploading audio chunks: " + audioFile.getOriginalFilename() + ", chunk-id: " + chunkId);
    return ResponseEntity.status(HttpStatus.CREATED).body(":D");
  }
}
