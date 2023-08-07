package com.dnd.MusicLog.user.entity;

import com.dnd.MusicLog.user.enums.OAuthType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id", nullable = false)
    private Long oauth_id;

    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_url", nullable = false)
    private String profile_url;

    @Column(name = "oauth_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthType oauth_type;

    @Builder
    public User(Long oauth_id, String email, String nickname, String profile_url, OAuthType oauth_type) {
        this.oauth_id = oauth_id;
        this.email = email;
        this.nickname = nickname;
        this.profile_url = profile_url;
        this.oauth_type = oauth_type;
    }
}
