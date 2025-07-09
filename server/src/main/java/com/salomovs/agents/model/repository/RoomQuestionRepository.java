package com.salomovs.agents.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salomovs.agents.model.entity.RoomQuestion;

public interface RoomQuestionRepository extends JpaRepository<RoomQuestion, String> {}
