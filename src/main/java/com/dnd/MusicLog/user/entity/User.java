package com.dnd.MusicLog.user.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.user.enums.OAuthType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "oauth_id", nullable = false)
    private long oauthId;

    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_url", nullable = false)
    private String profileUrl;

    @Column(name = "oauth_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthType oauthType;

    @Builder
    public User(long oauthId, String email, String nickname, String profileUrl, OAuthType oauthType) {
        this.oauthId = oauthId;
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.oauthType = oauthType;
    }
}
