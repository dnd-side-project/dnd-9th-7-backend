package com.dnd.MusicLog.user.service;

import com.dnd.MusicLog.user.oauth.OAuthApiClient;
import com.dnd.MusicLog.user.oauth.OAuthInfoResponse;
import com.dnd.MusicLog.user.oauth.OAuthLoginParams;
import com.dnd.MusicLog.user.enums.OAuthType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthType, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
            Collectors.toUnmodifiableMap(OAuthApiClient::oAuthType, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthType());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}
