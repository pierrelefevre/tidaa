package com.metaberse.postAPI.requestHandler;

import java.io.IOException;

import com.metaberse.postAPI.service.BackendException;

import okhttp3.*;

public class RequestHandler {

    private final static String userURL = "https://metaberse-userapi.herokuapp.com";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static boolean validateToken(String token) throws BackendException {
        try {
            if (token == null)
                throw new BackendException("Missing token");
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            RequestBody body = RequestBody.create(token, JSON);
            Request request = new Request.Builder()
                    .url(userURL + "/validate")
                    .method("POST", body).build();

            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            throw new BackendException("Error while validating token: " + e.getMessage());
        }
    }
}
