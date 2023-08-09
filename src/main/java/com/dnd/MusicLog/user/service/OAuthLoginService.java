package com.dnd.MusicLog.user.service;

import com.dnd.MusicLog.global.jwt.service.AuthTokensGeneratorService;
import com.dnd.MusicLog.global.jwt.dto.AuthTokensResponseDto;
import com.dnd.MusicLog.user.oauth.OAuthInfoResponse;
import com.dnd.MusicLog.user.oauth.OAuthLoginParams;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final UserRepository userRepository;
    private final AuthTokensGeneratorService authTokensGeneratorService;
    private final RequestOAuthInfoService requestOAuthInfoService;

    @Transactional
    public AuthTokensResponseDto loginUser(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = validateUserByEmail(oAuthInfoResponse);
        return authTokensGeneratorService.generateAuthToken(userId);
    }

    private Long validateUserByEmail(OAuthInfoResponse oAuthInfoResponse) {
        Optional<User> userOptional = userRepository.findByOauthId(oAuthInfoResponse.getOAuthId());

        if (userOptional.isPresent()) {
            return userOptional.get().getId();
        } else {
            return saveUser(oAuthInfoResponse);
        }

    }

    private Long saveUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
            .oauthId(oAuthInfoResponse.getOAuthId())
            .email(oAuthInfoResponse.getEmail())
            .nickname(oAuthInfoResponse.getNickname())
            .profileUrl(oAuthInfoResponse.getProfileUrl())
            .oauthType(oAuthInfoResponse.getOAuthType())
            .build();

        return userRepository.save(user).getId();
    }
}
