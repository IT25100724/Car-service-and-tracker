package com.example.car_service.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            int status = Integer.parseInt(statusCode.toString());
            String uri = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
            if (uri != null && uri.startsWith("/api/")) {
                return "forward:/error-api";
            }
            if (status == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/error/404.html";
            }
            if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "redirect:/error/500.html";
            }
        }
        return "redirect:/error/error.html";
    }
}