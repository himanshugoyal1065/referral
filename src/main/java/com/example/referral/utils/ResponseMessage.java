package com.example.referral.utils;

import lombok.Getter;

@Getter
public class ResponseMessage {

    private boolean isSuccess;
    private String message;

    public ResponseMessage(final boolean isSuccess, final String inMessage) {
        this.isSuccess = isSuccess;
        message = inMessage;
    }

}
