package com.salomovs.agents.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.salomovs.agents.model.entity.Room;

public record RoomResponse(
  String slug,
  String title,
  String description,
  LocalDateTime createdAt,
  List<RoomQuestionResponse> questions
) {
  public static RoomResponse parse(Room room) {
    List<RoomQuestionResponse> questions = room.getQuestions()
      .stream()
      .map(RoomQuestionResponse::parse)
      .collect(Collectors.toList());

    return new RoomResponse(
      room.getSlug(),
      room.getTitle(),
      room.getDescription(),
      room.getCreatedAt(),
      questions);
  }
}
