package com.salomovs.agents.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.agents.model.entity.RoomQuestion;

@Repository
public interface RoomQuestionRepository extends JpaRepository<RoomQuestion, String> {}
