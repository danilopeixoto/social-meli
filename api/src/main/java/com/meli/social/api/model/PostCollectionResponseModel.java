package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCollectionResponseModel {
  @Valid
  @NotNull(message = "The account field is required.")
  @JsonProperty("account")
  private AccountModel account;

  @Valid
  @NotNull(message = "The posts field is required.")
  @JsonProperty("posts")
  private List<PostResponseModel> posts;
}
