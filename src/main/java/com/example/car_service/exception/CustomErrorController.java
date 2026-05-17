package com.example.car_service.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CUSTOM ERROR CONTROLLER
 * ========================
 * Spring Boot has a default /error route that handles server-side errors.
 * This controller overrides that default behavior.
 *
 * - API errors (paths starting with /api/) are handled by GlobalExceptionHandler (returns JSON).
 * - Browser page errors (404, 500) redirect to static HTML error pages.
 *
 * OOP Concept: INTERFACE IMPLEMENTATION — implements ErrorController (Spring's interface).
 * Kept because: Spring Boot requires at least one ErrorController to handle /error routing.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Called automatically by Spring when any error occurs.
     * Checks the HTTP status code and routes to the appropriate page.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Get the HTTP status code (e.g., 404, 500)
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            int status = Integer.parseInt(statusCode.toString());

            // Get the URL path that caused the error
            String uri = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

            // For API calls: let the GlobalExceptionHandler return JSON (don't redirect to HTML)
            if (uri != null && uri.startsWith("/api/")) {
                return "forward:/error-api";
            }

            // For browser page errors: redirect to static error pages
            if (status == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/error/404.html";
            }
            if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "redirect:/error/500.html";
            }
        }

        // Fallback — redirect to a generic error page
        return "redirect:/error/error.html";
    }
}