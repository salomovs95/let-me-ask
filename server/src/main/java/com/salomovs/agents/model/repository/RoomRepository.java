package com.salomovs.agents.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.agents.model.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
  Optional<Room> findBySlug(String slug);
}
