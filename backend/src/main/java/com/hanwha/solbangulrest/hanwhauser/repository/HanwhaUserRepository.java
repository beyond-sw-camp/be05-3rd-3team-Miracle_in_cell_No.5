package com.hanwha.solbangulrest.hanwhauser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;

public interface HanwhaUserRepository extends JpaRepository<HanwhaUser, Long> {

	Optional<HanwhaUser> findHanwhaUserByGitEmail(String gitEmail);
}
