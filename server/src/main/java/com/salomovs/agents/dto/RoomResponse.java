package com.salomovs.agents.dto;

import java.time.LocalDateTime;

import com.salomovs.agents.model.entity.Room;

public record RoomResponse(
  String slug,
  String title,
  String description,
  LocalDateTime createdAt
) {
  public static RoomResponse parse(Room room) {
    return new RoomResponse(
      room.getSlug(),
      room.getTitle(),
      room.getDescription(),
      room.getCreatedAt());
  }
}
