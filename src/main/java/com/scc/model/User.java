package com.scc.models;

public record User(
        int id,
        String username,
        String email,
        String passwordHash,
        String role,
        String status
) {}