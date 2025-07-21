package com.salomovs.agents.model.entity;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RoomQuestionAnswer {
  private String answerText;
  private Float answerSimilarity;
}
