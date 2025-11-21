package com.schedule.app.security;

public record LoginRequest(
    String userId,
    String password) {
}