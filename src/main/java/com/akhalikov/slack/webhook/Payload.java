package com.akhalikov.slack.webhook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {
  @JsonProperty
  public String channel;
  @JsonProperty
  public String username;
  @JsonProperty
  public String text;
  @JsonProperty
  public String icon;

  Payload(String channel, String username, String text, String icon) {
    this.channel = channel;
    this.username = username;
    this.text = text;
    this.icon = icon;
  }
}
