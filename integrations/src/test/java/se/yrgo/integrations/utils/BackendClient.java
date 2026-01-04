package se.yrgo.integrations.utils;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BackendClient {
    private final HttpClient client;

    public BackendClient() {
        CookieManager cookieHandler = new CookieManager();
        this.client = HttpClient.newBuilder()
                .cookieHandler(cookieHandler)
                .build();
    }

    public String getBooksOnLoan() throws IOException, InterruptedException {
        // login data as a json string
        String loginData = "{\"username\":\"test2\",\"password\":\"yrgoP4ssword\"}";

        HttpRequest loginReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api/login"))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginData)).build();
        HttpResponse<String> loginResp = client.send(loginReq,
                HttpResponse.BodyHandlers.ofString());

        if (loginResp.statusCode() != 200) {
            throw new IllegalStateException("Could not log in");
        }

        HttpRequest lendReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/api/user/loans"))
                .build();
        HttpResponse<String> bookResp = client.send(lendReq,
                HttpResponse.BodyHandlers.ofString());

        return bookResp.body();
    }

    public boolean containsBookCopy(String json, int userId, int bookCopyId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);


            for (JsonNode loan : root) {
                int actualBookId = loan.path("book").path("id").asInt();
                int actualUserId = loan.path("user").asInt();

                if (actualBookId == bookCopyId && actualUserId == userId) {
                    return true;
                }
            }
            return false;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
