package com.salomovs.agents.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salomovs.agents.model.entity.AudioChunk;

@Repository
public interface AudioChunkRepository extends JpaRepository<AudioChunk, String> {
  @Query(
    value="""
      SELECT c.*
      FROM t_audio_chunks c
      JOIN (SELECT ?2 AS embeddings) AS q
      ON 1 - (c.embeddings::vector <=> q.embeddings::vector) > 0.7
      WHERE c.room_id = ?1
      ORDER BY 1 - (c.embeddings::vector <=> q.embeddings::vector)
      LIMIT 3;
    """,
    nativeQuery=true
  )
  List<AudioChunk> findByRoomId(String roomId,
                                String embeddings);
}
