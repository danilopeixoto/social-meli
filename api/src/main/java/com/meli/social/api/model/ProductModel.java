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
@Table("Product")
public class ProductModel {
  @NotNull(message = "The ID field is required.")
  @Min(value = 0, message = "The ID field requires a positive integer identifier.")
  @JsonProperty("id")
  @Column("id")
  private Integer id;

  @NotNull(message = "The account ID field is required.")
  @Min(value = 0, message = "The account ID field requires a positive integer identifier.")
  @JsonProperty("account_id")
  @Column("account_id")
  private Integer accountId;

  @NotBlank(message = "The type field is required.")
  @JsonProperty("type")
  @Column("type")
  private String type;

  @NotBlank(message = "The brand field is required.")
  @JsonProperty("brand")
  @Column("brand")
  private String brand;

  @NotBlank(message = "The description field is required.")
  @JsonProperty("description")
  @Column("description")
  private String description;
}
