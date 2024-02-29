package com.IOT.MiniProject.controller;

import lombok.Data;

@Data
public class APIResponse {
    private String status;
    private String message;
    private Object dataValue;
}
