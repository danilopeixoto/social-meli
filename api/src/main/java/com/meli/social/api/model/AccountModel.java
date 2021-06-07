package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("account")
public class AccountModel {
  @NotNull(message = "The ID field is required.")
  @Min(value = 0, message = "The ID field requires a positive integer identifier.")
  @JsonProperty("id")
  @Column("id")
  @Id
  private Integer id;

  @NotBlank(message = "The username field is required.")
  @JsonProperty("username")
  @Column("username")
  private String username;

  @NotNull(message = "The verified field is required.")
  @JsonProperty("verified")
  @Column("verified")
  private Boolean verified;
}
