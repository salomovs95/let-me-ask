package com.salomovs.agents.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.model.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepo;

  public List<Room> listRooms() {
    return roomRepo.findAll();
  }
}
