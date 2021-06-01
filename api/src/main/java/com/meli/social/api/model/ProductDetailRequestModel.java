package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
