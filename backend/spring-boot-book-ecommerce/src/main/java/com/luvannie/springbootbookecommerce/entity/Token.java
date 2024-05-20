package com.luvannie.springbootbookecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column
    private Boolean revoked;

    @Column
    private Boolean expired;

    @Column(name = "refresh_expiration_date")
    private LocalDateTime refreshExpirationDate;

    @Column(name = "is_mobile", columnDefinition = "TINYINT(1)")
    private boolean isMobile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public boolean isRevoked() {
        return revoked;
    }
}
