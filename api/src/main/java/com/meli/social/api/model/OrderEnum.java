package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderEnum {
  @JsonProperty("descending")
  Descending,

  @JsonProperty("ascending")
  Ascending
}
