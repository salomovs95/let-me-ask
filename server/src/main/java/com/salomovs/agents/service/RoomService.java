package com.salomovs.agents.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salomovs.agents.dto.AnswerQuestionDto;
import com.salomovs.agents.dto.CreateAnswerDto;
import com.salomovs.agents.dto.CreateQuestionDto;
import com.salomovs.agents.dto.CreateRoomDto;
import com.salomovs.agents.exception.AudioChunkProcessingException;
import com.salomovs.agents.exception.DataNotFoundException;
import com.salomovs.agents.model.entity.AudioChunk;
import com.salomovs.agents.model.entity.Room;
import com.salomovs.agents.model.entity.RoomQuestion;
import com.salomovs.agents.model.entity.RoomQuestionAnswer;
import com.salomovs.agents.model.repository.RoomQuestionRepository;
import com.salomovs.agents.model.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class RoomService {
  private final AIService aiService;
  private final RoomRepository roomRepo;
  private final RoomQuestionRepository questionRepo;

  public List<Room> listRooms() {
    return roomRepo.findAll();
  }

  public String createRoom(CreateRoomDto dto) {
    Room newRoom = roomRepo.save(new Room(dto.title(), dto.description().orElse(null)));
    return newRoom.getId();
  }

  public AnswerQuestionDto createQuestion(String slug, CreateQuestionDto dto) {
    Room room = findRoom(slug);
    RoomQuestion question = new RoomQuestion(dto.question(), room);
    question = questionRepo.save(question);

    try {
      String questionEmbeddings = aiService
        .generateEmbeddings(dto.question())
        .toString();

      List<AudioChunk> chunks = aiService.getAudioChunks(room.getId(), questionEmbeddings);

      List<String> transcriptions = chunks
        .stream()
        .map((chunk)->chunk.getTranscription())
        .collect(Collectors.toList());

      String answer = aiService.generateAnswer(questionRepo.save(question), transcriptions);
      Float answerSimilarity = answer.startsWith("Oh mai gáh!") ? 0f : aiService.calculateAnswerSimilarity(question.getQuestion(), answer);

      question.setAnswer(new RoomQuestionAnswer(answer.replace("Oh mai gáh! ", ""), answerSimilarity));
      questionRepo.save(question);

      return new AnswerQuestionDto(question.getId(), answer, answerSimilarity);
    } catch(Exception e) {
      return null;
    }
  }

  public AnswerQuestionDto answerQuestion(String roomSlug, CreateAnswerDto dto) {
    RoomQuestion question = questionRepo.findById(dto.questionId()).orElseThrow(()-> new EntityNotFoundException("Question not found"));
    Float answerSimilarity = aiService.calculateAnswerSimilarity(question.getQuestion(), dto.answer());

    question.setAnswer(new RoomQuestionAnswer(dto.answer(), answerSimilarity));
    questionRepo.save(question);

    return new AnswerQuestionDto(dto.questionId(), dto.answer(), answerSimilarity);
  }

  public Room findRoom(String slug) {
    Room room = roomRepo
      .findBySlug(slug)
      .orElseThrow(()->new DataNotFoundException("Room Not Found Or Not Exists"));
    return room;
  }

  public String handleUploadAudio(String roomId, MultipartFile audioFile) {
    try {
      byte[] audioBytes = audioFile.getBytes();
      String transcription = aiService.generateTranscription(audioBytes, audioFile.getContentType());
      List<Float> embeddings = aiService.generateEmbeddings(transcription);
      String audioChunkId = aiService.saveAudioChunk(transcription, roomId, embeddings);

      return audioChunkId;
    } catch (IOException e) {
      throw new AudioChunkProcessingException("Failed uploading audio file!");
    }
  }
}
