package com.dnd.MusicLog.user.oauth;

import com.dnd.MusicLog.user.enums.OAuthType;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthType oAuthType();
    MultiValueMap<String, String> makeBody();
}
