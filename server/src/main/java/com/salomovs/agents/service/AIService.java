package com.salomovs.agents.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.ContentEmbedding;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import com.salomovs.agents.exception.AudioChunkProcessingException;
import com.salomovs.agents.model.entity.AudioChunk;
import com.salomovs.agents.model.entity.RoomQuestion;
import com.salomovs.agents.model.repository.AudioChunkRepository;

@Component
@RequiredArgsConstructor
public class AIService {
  private final Client aiClient;
  private final AudioChunkRepository audioChunkRepository;

  public String generateTranscription(byte[] audioBytes, String mimeType) {
    final String model = "gemini-2.5-flash";

    Content content = Content.fromParts(
      Part.fromText("Transcreva o áudio para português do Brasil. Seja preciso e natural na transcrição. Mantenha a pontuação adequada e divida o texto em parágrafos quando for apropriado."),
      Part.fromBytes(audioBytes, mimeType)
    );

    var result = aiClient.models.generateContent(model, content, GenerateContentConfig.builder().build());
    if (result.text().isBlank() || result.text() == null) {
      throw new AudioChunkProcessingException("Can't transcribe given audio");
    }

    return result.text();
  }

  public List<Float> generateEmbeddingsF(String text) {
    final String model = "text-embedding-004";
    EmbedContentResponse result = aiClient.models.embedContent(model, List.of(text), EmbedContentConfig.builder().taskType("RETRIEVAL_DOCUMENT").build());

    List<ContentEmbedding> contents = result
      .embeddings()
      .orElseThrow(()->new AudioChunkProcessingException("Couldn't proccess transcription at this time!"));

    List<Float> embeddings = contents
      .get(0)
      .values()
      .orElseThrow(()->new AudioChunkProcessingException("Couldn't proccess transcription at this time!"));

    return embeddings;
  }

  public String generateAnswer(RoomQuestion question, List<String> transcriptions) {
    final String model = "gemini-2.5-flash";

    String context = transcriptions.stream()
      .reduce("", (acc, nextItem)->acc.concat(nextItem.concat("\n\n")));

    String prompt = String.format("""
      Com base no texto fornecido abaixo como contexto, responda a pergunta de forma clara e precisa em português do Brasil.
      CONTEXTO:
      %s
      PERGUNTA:
      %s

      INSTRUÇÕES:
      - Use apenas informações contidas no contexto enviado;
      - Se a resposta não for encontrada no contexto, apenas responda que não possui informações suficientes para responder;
      - Seja objetivo;
      - Mantenha um tom educativo e profissional;
      - Cite trechos relevantes do contexto se apropriado;
      - Se for citar o contexto, utilize o termo \"conteúdo da aula\";
      - Se caso não houver contexto ou o contexto não for suficiente, inicie a sua resposta com o termo \"Oh mai gáh!\";
    """, context, question.getQuestion());

    GenerateContentResponse result = aiClient.models.generateContent(model, prompt, null);

    if (result.text().isBlank() || result.text() == null) {
      throw new AudioChunkProcessingException("Couldn't generate answer this time!");
    }

    return result.text();
  }

  public String saveAudioChunk(String transcription, String roomId, List<Float> embeddings) {
    String chunkId = audioChunkRepository.save(new AudioChunk(null, transcription, embeddings, roomId, null)).getId();
    return chunkId;
  }

  public List<AudioChunk> getAudioChunks(String roomId, String embeddings) {
    return audioChunkRepository.findByRoomId(roomId, embeddings);
  }
}
