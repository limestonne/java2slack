package com.akhalikov.slack.webhook;

public enum SlackIcon {
  DEFAULT(":slack:");

  public final String text;

  SlackIcon(String text) {
    this.text = text;
  }
}
