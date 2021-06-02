package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRequestModel {
  @NotBlank(message = "The username field is required.")
  @JsonProperty("username")
  private String username;
}
