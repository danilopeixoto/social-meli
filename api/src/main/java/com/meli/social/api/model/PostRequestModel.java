package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequestModel {
  @NotNull(message = "The category field is required.")
  @Min(value = 0, message = "The category field requires a positive integer identifier.")
  @JsonProperty("category")
  private Integer category;

  @NotNull(message = "The promotion field is required.")
  @JsonProperty("promotion")
  private Boolean promotion;

  @NotNull(message = "The price field is required.")
  @Min(value = 0, message = "The price field requires a positive number.")
  @JsonProperty("price")
  private Double price;

  @NotNull(message = "The discount field is required.")
  @Min(value = 0, message = "The discount field requires a positive number.")
  @JsonProperty("discount")
  private Double discount;

  @Valid
  @NotNull(message = "The product field is required.")
  @JsonProperty("product")
  private ProductDetailRequestModel product;
}
