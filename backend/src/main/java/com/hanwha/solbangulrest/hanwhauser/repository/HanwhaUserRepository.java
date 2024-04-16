package com.hanwha.solbangulrest.hanwhauser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;

public interface HanwhaUserRepository extends JpaRepository<HanwhaUser, Long> {

	Optional<HanwhaUser> findHanwhaUserByGitEmail(String gitEmail);

	Boolean existsByGitEmail(String gitEmail);

	@Query("select h.isMember from HanwhaUser h where h.gitEmail = :gitEmail")
	boolean isMemberByGitEmail(String gitEmail);
}
