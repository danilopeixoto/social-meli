package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderEnum {
  @JsonProperty("desc")
  Descending,

  @JsonProperty("asc")
  Ascending;

  public String shortName() {
    return this == Descending ? "desc" : "asc";
  }
}
