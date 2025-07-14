package com.salomovs.agents.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsController {
  private final RoomService roomService;

  @Tag(name="Room")
  @Operation(
    summary="List of open rooms",
    responses={
      @ApiResponse(
        responseCode="200",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=List.class, example="[ {\"slug\":\"nlw-20-agents-intermediate\", \"title\":\"NLW 20 Agents Intermediate\", \"description\":\"A room to talk about the challenges and doubts in the course of the event.\", \"createdAt\":\"2025-07-07T00:00:00z\", \"questions\":[]} ]")
        )
      ),
      @ApiResponse(
        responseCode="500",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      )
    }
  )
  @GetMapping
  public ResponseEntity<List<RoomResponse>> getRooms() {
    List<RoomResponse> rooms = roomService.listRooms()
      .stream()
      .map(RoomResponse::parse)
      .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }

  @Tag(name="Room")
  @Operation(
    summary="Open a new Q&A room",
    responses={
      @ApiResponse(
        responseCode="201",
        content=@Content(mediaType="application/json")
      ),
      @ApiResponse(
        responseCode="400",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="500",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      )
    }
  )
  @PostMapping
  public ResponseEntity<Void> openRoom(@RequestBody CreateRoomDto body) {
    String roomId = roomService.createRoom(body);
    log.warn("New Room Opened: " + roomId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Tag(name="Room")
  @Operation(
    summary="Retrieves data for a given room",
    responses={
      @ApiResponse(
        responseCode="200",
         content=@Content(
           mediaType="application/json",
           schema=@Schema(implementation=RoomResponse.class, example="{\"slug\":\"nlw20-agents-intermediate\", \"title\":\"NLW20 Agents Intermediate\", \"description\":\"A room to talk about the challenges and doubts in the course of the event.\", \"createdAt\":\"2025-07-07T00:00:00z\", \"questions\":[]}")
         )
      ),
      @ApiResponse(
        responseCode="404",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="500",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      )
    }
  )
  @GetMapping("/{room_slug}")
  public ResponseEntity<RoomResponse> findRoomBySlug(@PathVariable(name="room_slug") String roomSlug) {
    Room room = roomService.findRoom(roomSlug);
    RoomResponse res = RoomResponse.parse(room);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @Tag(name="Question")
  @Operation(
    summary="Post a new question on a given room",
    responses={
      @ApiResponse(
        responseCode="201",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=AnswerQuestionDto.class, example="{\"questionId\":\"What's happening today?\", \"answer\":\"The greatest programming event alive.\"}")
        )
      ),
      @ApiResponse(
        responseCode="400",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="404",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="500",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      )
    }
  )
  @PostMapping("/{room_slug}/questions")
  public ResponseEntity<AnswerQuestionDto> placeQuestion(@PathVariable(name="room_slug") String roomSlug, @RequestBody CreateQuestionDto body) {
    AnswerQuestionDto response = roomService.createQuestion(roomSlug, body);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Tag(name="Room")
  @Operation(
    summary="Upload audio for answer generation context",
    responses={
      @ApiResponse(
        responseCode="201",
        content=@Content(mediaType="application/json")
      ),
      @ApiResponse(
        responseCode="400",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="404",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      ),
      @ApiResponse(
        responseCode="500",
        content=@Content(
          mediaType="application/json",
          schema=@Schema(implementation=Map.class, example="{\"error\":\"something unexpected happened\"}")
        )
      )
    }
  )
  @PostMapping("/{room_slug}/audio")
  public ResponseEntity<Void> uploadAudio(@PathVariable(name="room_slug") String roomSlug, @RequestParam("file") MultipartFile audioFile) {
    Room room = roomService.findRoom(roomSlug);
    String chunkId = roomService.handleUploadAudio(room.getId(), audioFile);

    log.warn("Uploading audio chunks: " + audioFile.getOriginalFilename() + ", chunk-id: " + chunkId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
