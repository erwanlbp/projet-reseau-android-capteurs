package http;

import com.squareup.okhttp.*;

import java.io.IOException;

public class Requests {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    public Requests() {
        this.client = new OkHttpClient();
    }

    public String sendGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String sendPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}