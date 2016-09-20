package com.akhalikov.java2slack;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Webhook {
  private static final String WEBHOOK_URL = "https://hooks.slack.com/services/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";
  private static final String MESSAGE_TEMPLATE =
      "payload={" +
          "'channel': '%s', " +
          "'username': 'hhru-settings-bot', " +
          "'text': '%s', " +
          "'icon_emoji': ':ghost:'" +
          "}";

  private AsyncHttpClient asyncHttpClient;

  public Webhook(AsyncHttpClient asyncHttpClient) {
    this.asyncHttpClient = asyncHttpClient;
  }

  public CompletableFuture<Response> notifyChannel(String channel, String message) throws InterruptedException, ExecutionException {
    return asyncHttpClient
        .preparePost(WEBHOOK_URL)
        .setBody(String.format(MESSAGE_TEMPLATE, channel, message))
        .execute()
        .toCompletableFuture();
  }
}
