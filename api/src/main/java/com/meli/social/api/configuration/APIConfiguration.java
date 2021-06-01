package com.meli.social.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "api")
@Configuration
public class APIConfiguration {
  @NotBlank(message = "The version field is required.")
  private String version;
}
