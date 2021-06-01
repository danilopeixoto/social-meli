package com.meli.social.api.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Builder(toBuilder = true)
@Getter
@Setter
@ConfigurationProperties(prefix = "api")
@Configuration
public class APIConfiguration {
  @NotBlank(message = "The version field is required.")
  @JsonProperty("version")
  private String version;
}
