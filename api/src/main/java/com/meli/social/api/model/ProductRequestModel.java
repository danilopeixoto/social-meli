package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestModel {
  @NotNull(message = "The account ID field is required.")
  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("account_id")
  private Integer accountId;

  @NotBlank(message = "The name field is required.")
  @JsonProperty("name")
  private String name;

  @NotBlank(message = "The type field is required.")
  @JsonProperty("type")
  private String type;

  @NotBlank(message = "The brand field is required.")
  @JsonProperty("brand")
  private String brand;

  @NotBlank(message = "The color field is required.")
  @JsonProperty("color")
  private String color;

  @NotBlank(message = "The description field is required.")
  @JsonProperty("description")
  private String description;
}
