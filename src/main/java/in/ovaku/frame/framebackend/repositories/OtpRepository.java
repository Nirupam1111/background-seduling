package in.ovaku.frame.framebackend.repositories;
/*
 * Copyright (c) 2022 Ovaku.
 */

import in.ovaku.frame.framebackend.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This is a repository interface which provides crud operation for {@link Otp}.
 *
 * @author Nirupam
 * @version 1.0
 * @since 26/01/2023
 */
public interface OtpRepository extends JpaRepository<Otp, Long> {
    /**
     * Find all {@link Otp} by phoneNo and otp.
     *
     * @param phoneNo - phone no of the user.
     * @param otp - otp, sent for validation.
     * @return - Optional
     */
    Optional<Otp> findByPhoneNoAndOtp(Long phoneNo, Integer otp);

    /**
     * Deletes the {@link Otp} by phoneNo and otp.
     *
     * @param phoneNo - phone no of the user.
     * @param otp - otp, sent for validation.
     * @return int
     */
    int deleteByPhoneNoAndOtp(Long phoneNo, Integer otp);
}
