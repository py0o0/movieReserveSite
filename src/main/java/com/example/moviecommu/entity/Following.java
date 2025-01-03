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
    private Long id;

    private String userId;
    private String flwName;

    @Builder
    public Following(String userId, String flwName) {
        this.userId = userId;
        this.flwName = flwName;
    }
}
