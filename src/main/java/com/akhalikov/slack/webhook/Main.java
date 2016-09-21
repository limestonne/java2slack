package com.akhalikov.slack.webhook;

public class Main {
  public static void main(String[] args) throws Exception {
    final String webhookUrl = "https://hooks.slack.com/services";

    SlackClient slack = new SlackClient.Builder(webhookUrl, "test-channel")
        .build();

    slack.push("Hello, Slack!");
  }
}
