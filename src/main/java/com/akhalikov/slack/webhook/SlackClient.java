package com.akhalikov.slack.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

public class SlackClient {
  private final String webhookUrl;
  private final String channel;
  private final String username;
  private final SlackIcon icon;
  private final AsyncHttpClient asyncHttpClient;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private SlackClient(String webhookUrl, String channel, String username,
                      SlackIcon icon, AsyncHttpClient asyncHttpClient) {
    this.webhookUrl = webhookUrl;
    this.channel = channel;
    this.username = username;
    this.icon = icon;
    this.asyncHttpClient = asyncHttpClient;
  }

  public ListenableFuture<Response> push(String message) {
    Payload payload = new Payload(message, channel, username, icon.text);
    final Request request = asyncHttpClient.preparePost(webhookUrl)
        .addFormParam("payload", convertToJson(payload))
        .build();
    return asyncHttpClient.executeRequest(request);
  }

  private static String convertToJson(Payload slackMessage) {
    try {
      return objectMapper.writeValueAsString(slackMessage);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Cannot convert message to json", e);
    }
  }

  public static class Builder {
    private String webhookUrl;
    private String channel;
    private AsyncHttpClient asyncHttpClient;
    private String username;
    private SlackIcon icon;

    public Builder(String webhookUrl, String channel) {
      this.webhookUrl = webhookUrl;
      this.channel = channel;
    }

    public Builder setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
      this.asyncHttpClient = asyncHttpClient;
      return this;
    }

    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder setIcon(SlackIcon icon) {
      this.icon = icon;
      return this;
    }

    public SlackClient build() {
      if (StringUtils.isEmpty(webhookUrl)) {
        throw new IllegalArgumentException("Cannot create SlackClient: webhookUrl is not provided");
      }
      if (StringUtils.isEmpty(channel)) {
        throw new IllegalArgumentException("Cannot create SlackClient: channel is not provided");
      }
      if (!channel.startsWith("#")) {
        channel = "#" + channel;
      }
      if (asyncHttpClient == null) {
        asyncHttpClient = new DefaultAsyncHttpClient(defaultAsyncHttpClientConfig());
      }
      if (icon == null) {
        icon = SlackIcon.DEFAULT;
      }
      return new SlackClient(webhookUrl, channel, username, icon, asyncHttpClient);
    }
  }

  private static AsyncHttpClientConfig defaultAsyncHttpClientConfig() {
    return new DefaultAsyncHttpClientConfig.Builder()
        .setUserAgent("java-slack-client")
        .setMaxConnections(1)
        .setMaxRequestRetry(2)
        .setRequestTimeout(500)
        .build();
  }
}
