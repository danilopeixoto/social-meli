package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCountResponse {
  @Valid
  @NotNull(message = "The account field is required.")
  @JsonProperty("account")
  private AccountModel account;

  @NotNull(message = "The count field is required.")
  @Min(value = 0, message = "The count field requires a positive integer.")
  @JsonProperty("count")
  private Integer count;
}
