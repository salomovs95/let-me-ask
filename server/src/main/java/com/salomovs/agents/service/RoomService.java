package com.salomovs.agents.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salomovs.agents.dto.CreateQuestionDto;
import com.salomovs.agents.dto.CreateRoomDto;
import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.model.entity.RoomQuestion;
import com.salomovs.agents.model.repository.RoomQuestionRepository;
import com.salomovs.agents.model.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepo;
  private final RoomQuestionRepository questionRepo;

  public List<Room> listRooms() {
    return roomRepo.findAll();
  }

  public String createRoom(CreateRoomDto dto) {
    Room newRoom = roomRepo.save(new Room(dto.title(), dto.description().orElse(null)));
    return newRoom.getId();
  }

  public String createQuestion(String slug, CreateQuestionDto dto) {
    Room room = findRoom(slug);
    RoomQuestion question = questionRepo.save(new RoomQuestion(dto.question(), room));
    return question.getId();
  }

  public Room findRoom(String slug) {
    Room room = roomRepo.findBySlug(slug)
                        .orElseThrow(()->new RuntimeException("Room Not Found Or Not Exists"));
    return room;
  }
}
