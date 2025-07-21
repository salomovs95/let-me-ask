package com.salomovs.agents.model.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomQuestion {
  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private String id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name="room_id", referencedColumnName="id")
  private Room room;

  @Column(columnDefinition="TEXT")
  private String question;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name="answerText", column=@Column(name="answer", columnDefinition="TEXT")),
    @AttributeOverride(name="answerSimilarity", column=@Column(name="answer_similarity_grade"))
  })
  private RoomQuestionAnswer answer;

  private LocalDateTime createdAt;

  public RoomQuestion(String question, Room room) {
    this.question = question;
    this.room = room;
    this.createdAt = LocalDateTime.now();
  }
}
