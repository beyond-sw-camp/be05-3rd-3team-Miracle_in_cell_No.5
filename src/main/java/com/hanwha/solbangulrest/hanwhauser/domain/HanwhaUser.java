package com.hanwha.solbangulrest.hanwhauser.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hanwha_user")
public class HanwhaUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hanwha_user_id")
	private Long id;

	private String username;
	private String gitEmail;

	@ColumnDefault("false")
	private Boolean isMember;

	@Builder
	public HanwhaUser(String username, String gitEmail, Boolean isMember) {
		this.username = username;
		this.gitEmail = gitEmail;
		this.isMember = isMember;
	}

	public void createAccount() {
		this.isMember = true;
	}

	public void deleteAccount() {
		this.isMember = false;
	}
}
