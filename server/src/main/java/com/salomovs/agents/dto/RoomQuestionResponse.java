package com.salomovs.agents.dto;

import java.time.LocalDateTime;

import com.salomovs.agents.model.entity.RoomQuestion;

public record RoomQuestionResponse(
  String id,
  String roomId,
  String question,
  String answer,
  LocalDateTime createdAt
) {
  public static RoomQuestionResponse parse(RoomQuestion question) {
    return new RoomQuestionResponse(
      question.getId(),
      question.getRoom().getId(),
      question.getQuestion(),
      question.getAnswer(),
      question.getCreatedAt());
  }
}
