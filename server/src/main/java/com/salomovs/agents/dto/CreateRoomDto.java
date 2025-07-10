package com.salomovs.agents.dto;

import java.util.Optional;

public record CreateRoomDto(
  String title,
  Optional<String> description
) {}
