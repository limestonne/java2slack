package com.akhalikov.java2slack;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) throws Exception {
    AsyncHttpClient asyncHttpClient = createAsyncHttpClient();

    Webhook webhook = new Webhook(asyncHttpClient);
    CompletableFuture<Response> future = webhook.notifyChannel("#test_settings", "Hello, Slack!");
    future.whenComplete();
  }

  private static AsyncHttpClient createAsyncHttpClient() {
    AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
        .setUserAgent("curl/7.35.0")
        .build();

    return new DefaultAsyncHttpClient(config);
  }
}
