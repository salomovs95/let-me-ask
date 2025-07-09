
package com.salomovs.agents.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="t_rooms")
public class Room {
  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private String id;

  @Column(nullable=false, unique=true)
  private String slug;

  @Column(nullable=false)
  private String title;

  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy="room")
  List<RoomQuestion> questions;

  public Room(String title, String description) {
    this.title = title;
    this.description = description;
    this.slug = title.replace(" ", "-").toLowerCase();
  }
}
