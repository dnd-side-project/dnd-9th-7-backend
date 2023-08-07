package com.dnd.MusicLog.user.oauth;

import com.dnd.MusicLog.user.enums.OAuthType;

public interface OAuthApiClient {
    OAuthType oAuthType();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
