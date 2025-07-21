package com.salomovs.agents.dto;

import java.time.LocalDateTime;

import com.salomovs.agents.model.entity.RoomQuestion;
import com.salomovs.agents.model.entity.RoomQuestionAnswer;

public record RoomQuestionResponse(
  String id,
  String roomId,
  String question,
  RoomQuestionAnswer answer,
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
