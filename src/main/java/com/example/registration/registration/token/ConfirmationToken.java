package com.example.registration.registration.token;

import java.time.LocalDateTime;

import com.example.registration.appuser.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    
    @SequenceGenerator(
        name = "confirmation_token_sequence",
        sequenceName = "confirmation_token_sequence",
        allocationSize = 1,
        initialValue = 1000
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "confirmation_token_sequence"
    )
    @Id
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
        nullable = false,
        name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
            AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
