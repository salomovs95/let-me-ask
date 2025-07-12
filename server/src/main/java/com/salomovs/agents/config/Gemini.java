package com.salomovs.agents.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.genai.Client;

@Component
public class Gemini {
  @Value("${gemini.api-key}")
  private String GEMINI_API_KEY;

  @Bean
  public Client getClient() {
    Client client = Client.builder().apiKey(GEMINI_API_KEY).build();
    return client;
  }
}
