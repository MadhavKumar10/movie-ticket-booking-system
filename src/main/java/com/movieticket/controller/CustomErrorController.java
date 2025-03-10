package com.movieticket.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public Map<String, Object> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, Object> response = new HashMap<>();

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            response.put("status", statusCode);
            response.put("message", "An error occurred: " + HttpStatus.valueOf(statusCode));
        } else {
            response.put("status", 500);
            response.put("message", "An unexpected error occurred.");
        }

        return response;
    }
}