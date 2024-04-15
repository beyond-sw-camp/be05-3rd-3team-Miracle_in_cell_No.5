package com.hanwha.solbangulrest.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	Optional<User> findByLoginId(String loginId);

	@Query("select u from User u where u.hanwhaUser.gitEmail = :gitEmail")
	Optional<User> findByGitEmail(String gitEmail);
}
