package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponseModel {
  @NotNull(message = "The ID field is required.")
  @Min(value = 0, message = "The ID field requires a positive integer identifier.")
  @JsonProperty("id")
  private Integer id;

  @NotNull(message = "The account ID field is required.")
  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("account_id")
  private Integer accountId;

  @NotNull(message = "The category field is required.")
  @Min(value = 0, message = "The category field requires a positive integer identifier.")
  @JsonProperty("category")
  private Integer category;

  @NotNull(message = "The promotional field is required.")
  @JsonProperty("promotional")
  private Boolean promotional;

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
  private ProductDetailResponseModel product;

  @NotNull(message = "The created at field is required.")
  @JsonProperty("created_at")
  private Instant createdAt;
}
