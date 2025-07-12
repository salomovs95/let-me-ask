package com.salomovs.agents.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_audio_chunks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AudioChunk {
  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private String id;

  private String transcription;

  private List<Float> embeddings;

  private String roomId;

  private LocalDateTime createdAt;
}
