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
public class ProductDetailRequestModel {
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
