package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequestModel {
  @NotNull(message = "The account ID field is required.")
  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("account_id")
  private Integer accountId;

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

  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("product_id")
  private Integer productId;

  @Valid
  @JsonProperty("product")
  private ProductDetailRequestModel product;

  @JsonIgnore
  public Optional<Integer> getOptionalProductId() {
    return Optional.ofNullable(this.productId);
  }

  @JsonIgnore
  public Optional<ProductDetailRequestModel> getOptionalProduct() {
    return Optional.ofNullable(this.product);
  }
}
