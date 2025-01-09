package com.example.moviecommu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Long userId;
    private Long flwingId;

    @Builder
    public Following(Long userId, Long flwingId) {
        this.userId = userId;
        this.flwingId = flwingId;
    }
}
