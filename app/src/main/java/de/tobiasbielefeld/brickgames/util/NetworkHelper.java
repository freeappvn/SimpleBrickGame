/*
 * *******************************************************************
 * Copyright (c) 2018 to present.
 * All rights reserved.
 *
 * Author: ldt
 * ******************************************************************
 *
 */

package de.tobiasbielefeld.brickgames.util;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.tobiasbielefeld.brickgames.model.DataResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ldt on 9/19/17.
 */

public class NetworkHelper {

    private static final String TAG = NetworkHelper.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static DataResponse queryData(String url, String content, Map<String, String> headers, Map<String, Object> queryParams, int timeOut, MethodType type) throws SocketTimeoutException, UnknownHostException {

        Request.Builder builder = new Request.Builder();

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // add body for request
        RequestBody body = null;
        if (content != null && !content.equals("")) {
            body = RequestBody.create(JSON, content);
        }
        switch (type) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
        }

        if (queryParams != null && queryParams.size() > 0) {
            url += "?";
            for (Map.Entry<String, Object> entry : queryParams.entrySet()
                    ) {
                url += entry.getKey() + "=" + entry.getValue() +"&";
            }
        }
        // add Url for Request
        builder.url(url);


        Request request = builder.build();

        Response response = null;
        try {
            OkHttpClient client = null;
            if (timeOut != 0) {
                client = getClient(timeOut);
            } else {
                client = getClient();
            }

            response = client.newCall(request).execute();
            String bodyString = response.body().string();
            return new DataResponse(response.code(), bodyString);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (UnknownHostException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return new DataResponse(404, "{\"ErrorCode\":0, \"Descirption\":\"\"}");
    }

    private static OkHttpClient client;

    static OkHttpClient getClient() {
        int timeOut = 5;
        if (client == null)
            client = new OkHttpClient.Builder()
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .writeTimeout(timeOut, TimeUnit.SECONDS)
                    .build();
        return client;
    }

    static OkHttpClient getClient(int timeOut) {
        if (timeOut == 0)
            return getClient();
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();
    }

    public enum MethodType {
        GET, PUT, POST, DELETE
    }
}
