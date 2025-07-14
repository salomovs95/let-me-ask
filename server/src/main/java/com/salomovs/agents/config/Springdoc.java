package com.salomovs.agents.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Springdoc {
  public OpenAPI openAPI() {
    Info info = (new Info()).title("Q&A Powered by A.I.");
    OpenAPI api = (new OpenAPI()).info(info);
    return api;
  }
}
