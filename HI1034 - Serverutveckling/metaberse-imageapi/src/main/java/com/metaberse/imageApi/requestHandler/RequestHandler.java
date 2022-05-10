package com.metaberse.imageApi.requestHandler;

import com.metaberse.imageApi.records.UploadPicture;
import com.metaberse.imageApi.service.BackendException;
import okhttp3.*;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class RequestHandler {

    private final static String userURL = "https://metaberse-userapi.herokuapp.com";
    private final static String chatURL = "https://metaberse-chatapi.herokuapp.com";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String postImageMessage(UploadPicture picture, long imageID) throws IOException, BackendException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        JSONObject jsonObject = new JSONObject()
                .put("chatId", picture.chatID())
                .put("senderId", picture.userID())
                .put("content", "/images/" + imageID)
                .put("type", "IMAGE")
                .put("token", picture.token());
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url(chatURL + "/messages")
                .method("POST", body).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new BackendException("Response from chat API failed: " + response.header("message"));
        }
        return response.body().string();
    }

    public static String updateUser(long userID, long imageID) throws BackendException, IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageUrl", "/images/" + imageID);
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url(userURL + "/users/" + userID)
                .method("PATCH", body).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.out.println("We fucked up" + response.message());
            throw new BackendException(response.header("message"));
        }
        return response.body().string();
    }
}
