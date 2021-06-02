package com.meli.social.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("follower")
public class FollowerModel {
  @NotNull(message = "The follower ID field is required.")
  @Min(value = 0, message = "The follower ID field requires a positive integer identifier.")
  @JsonProperty("follower_id")
  @Column("follower_id")
  private Integer followerId;

  @NotNull(message = "The followed ID field is required.")
  @Min(value = 0, message = "The followed ID field requires a positive integer identifier.")
  @JsonProperty("followed_id")
  @Column("followed_id")
  private Integer followedId;
}
