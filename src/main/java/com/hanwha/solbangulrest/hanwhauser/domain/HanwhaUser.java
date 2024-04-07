package com.hanwha.solbangulrest.hanwhauser.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class HanwhaUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hanwha_user_id")
	private Long id;

	private String username;
	private String gitEmail;
}
