package com.salomovs.agents.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.service.RoomService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsController {
  private final RoomService roomService;

  @GetMapping("")
  public ResponseEntity<List<Room>> getRooms() {
    List<Room> rooms = roomService.listRooms();
    return ResponseEntity.status(HttpStatus.OK).body(rooms);
  }
}
