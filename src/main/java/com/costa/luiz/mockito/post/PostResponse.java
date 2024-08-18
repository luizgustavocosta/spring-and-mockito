package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.user.User;

import java.time.LocalDateTime;

record PostResponse(Long id, String texto, User autor, LocalDateTime data) {
}