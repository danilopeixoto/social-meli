package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountResponseModel {
  @NotNull(message = "The count field is required.")
  @Min(value = 0, message = "The count field requires a positive integer.")
  @JsonProperty("count")
  private Integer count;
}
