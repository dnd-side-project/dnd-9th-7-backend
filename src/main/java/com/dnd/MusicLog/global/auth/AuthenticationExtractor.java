package com.dnd.MusicLog.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@RequiredArgsConstructor
@Component
public class AuthenticationExtractor {

    public String extract(NativeWebRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new IllegalArgumentException("token 이 없습니다");
        }

        return authorizationHeader;
    }
}
