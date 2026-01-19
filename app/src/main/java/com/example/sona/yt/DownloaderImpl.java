package com.example.sona.yt;

import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Request;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DownloaderImpl extends Downloader {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; rv:91.0) Gecko/20100101 Firefox/91.0";
    private final OkHttpClient client;

    private static DownloaderImpl instance;

    public DownloaderImpl() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        this.client = builder.build();
    }

    public static DownloaderImpl getInstance() {
        if (instance == null) {
            instance = new DownloaderImpl();
        }
        return instance;
    }

    @Override
    public Response execute(Request request) throws IOException, ReCaptchaException {
        String httpMethod = request.httpMethod();
        String url = request.url();
        Map<String, List<String>> headers = request.headers();
        byte[] data = request.dataToSend();

        okhttp3.Request.Builder okRequestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT);

        // Add headers
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue()) {
                okRequestBuilder.addHeader(entry.getKey(), value);
            }
        }

        // Handle POST/PUT payload
        RequestBody requestBody = null;
        if (data != null) {
            requestBody = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        }

        okRequestBuilder.method(httpMethod, requestBody);

        try (okhttp3.Response okResponse = client.newCall(okRequestBuilder.build()).execute()) {

            if (okResponse.code() == 429) {
                throw new ReCaptchaException("reCaptcha Challenge requested", url);
            }

            String responseBody = okResponse.body() != null ? okResponse.body().string() : "";
            Map<String, List<String>> responseHeaders = okResponse.headers().toMultimap();

            // latestUrl should not be null
            String latestUrl = okResponse.request().url().toString();

            return new Response(
                    okResponse.code(),
                    okResponse.message(),
                    responseHeaders,
                    responseBody,
                    latestUrl
            );
        }
    }
}
