package com.example.moviecommu.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flwId;

    // 팔로우 하는 사람 (나)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK 이름
    private User user;

    // 팔로우 당하는 사람 (상대)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flwing_id") // FK 이름
    private User following;

    @Builder
    public Following(User user, User following) {
        this.user = user;
        this.following = following;
    }
}
