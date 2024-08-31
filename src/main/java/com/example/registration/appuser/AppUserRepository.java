package com.example.registration.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findUserByUsername(String username);
    Optional<AppUser> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser u " +
            "SET u.enabled = TRUE " +
            "WHERE u.email = ?1")
    int enableAppUser(String email);
}
