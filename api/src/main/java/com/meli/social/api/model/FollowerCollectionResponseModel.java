package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FollowerCollectionResponseModel {
  @Valid
  @NotNull(message = "The account field is required.")
  @JsonProperty("account")
  private AccountModel account;

  @Valid
  @NotNull(message = "The users field is required.")
  @JsonProperty("users")
  private List<AccountModel> users;
}