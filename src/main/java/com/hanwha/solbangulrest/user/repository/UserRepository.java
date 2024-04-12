package com.hanwha.solbangulrest.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hanwha.solbangulrest.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	User findByLoginId(String loginId);
}
