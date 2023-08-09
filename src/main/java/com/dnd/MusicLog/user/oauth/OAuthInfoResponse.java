package com.dnd.MusicLog.user.oauth;

import com.dnd.MusicLog.user.enums.OAuthType;

public interface OAuthInfoResponse {

    String getOAuthId();
    String getEmail();
    String getNickname();
    String getProfileUrl();
    OAuthType getOAuthType();

}
