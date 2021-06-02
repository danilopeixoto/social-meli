package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("post")
public class PostModel {
  @NotNull(message = "The ID field is required.")
  @Min(value = 0, message = "The ID field requires a positive integer identifier.")
  @JsonProperty("id")
  @Column("id")
  @Id
  private Integer id;

  @NotNull(message = "The account ID field is required.")
  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("account_id")
  @Column("account_id")
  private Integer accountId;

  @NotNull(message = "The product ID field is required.")
  @Min(value = 0, message = "The product ID field requires a positive integer identifier.")
  @JsonProperty("product_id")
  @Column("product_id")
  private Integer productId;

  @NotNull(message = "The category field is required.")
  @Min(value = 0, message = "The category field requires a positive integer identifier.")
  @JsonProperty("category")
  @Column("category")
  private Integer category;

  @NotNull(message = "The promotion field is required.")
  @JsonProperty("promotion")
  @Column("promotion")
  private Boolean promotion;

  @NotNull(message = "The price field is required.")
  @Min(value = 0, message = "The price field requires a positive number.")
  @JsonProperty("price")
  @Column("price")
  private Double price;

  @NotNull(message = "The discount field is required.")
  @Min(value = 0, message = "The discount field requires a positive number.")
  @JsonProperty("discount")
  @Column("discount")
  private Double discount;

  @NotNull(message = "The created at field is required.")
  @JsonProperty("created_at")
  @Column("created_at")
  @CreatedDate
  private Instant createdAt;
}
