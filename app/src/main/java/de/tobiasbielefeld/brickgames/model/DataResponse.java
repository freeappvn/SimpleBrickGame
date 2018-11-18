/*
 * *******************************************************************
 * Copyright (c) 2018 to present.
 * All rights reserved.
 *
 * Author: ldt
 * ******************************************************************
 *
 */

package de.tobiasbielefeld.brickgames.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by ldt on 9/19/17.
 */

public class DataResponse {
    public static final int RESPONSE_OK= 200;
    private int status;
    private String body;

    public JsonElement jsonBody() {
        try {
            return new JsonParser().parse(getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonParser().parse("{\"ErrorCode\":0, \"Descirption\":\"\"}").getAsJsonObject();
    }

    public boolean isOK() {
        return status == RESPONSE_OK;
    }

    public boolean isTimeOut() {
        return status == RESPONSE_OK;
    }

    public DataResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public DataResponse(Response response) {
        this.status = response.code();
        try {
            this.body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
