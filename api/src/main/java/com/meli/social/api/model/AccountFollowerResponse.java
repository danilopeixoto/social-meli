package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountFollowerResponse {
  @Valid
  @NotNull(message = "The account field is required.")
  @JsonProperty("account")
  private AccountModel account;

  @Valid
  @NotNull(message = "The followers field is required.")
  @JsonProperty("followers")
  private List<AccountModel> followers;
}
