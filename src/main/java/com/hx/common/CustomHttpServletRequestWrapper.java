package com.hx.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dhx
 * @date 2025/1/8 23:20
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders = new HashMap<>();

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        return headerValue != null ? headerValue : super.getHeader(name);
    }

    @Override
    public java.util.Enumeration<String> getHeaderNames() {
        java.util.Set<String> headerNames = new java.util.HashSet<>(customHeaders.keySet());
        headerNames.addAll(Collections.list(super.getHeaderNames()));
        return Collections.enumeration(headerNames);
    }
}
