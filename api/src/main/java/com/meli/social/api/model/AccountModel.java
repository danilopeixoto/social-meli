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
@Table("Account")
public class AccountModel {
  @NotNull(message = "The ID field is required.")
  @Min(value = 0, message = "The ID field requires a positive integer identifier.")
  @JsonProperty("id")
  @Column("id")
  private Integer id;

  @NotBlank(message = "The username field is required.")
  @JsonProperty("username")
  @Column("username")
  private String username;
}
