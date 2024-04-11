package com.hanwha.solbangulrest.post.domain;

import lombok.Getter;

@Getter
public enum Category {
	CLAIMS("건의"), COMPLIMENT("칭찬"), FREE("자유");

	private final String value;

	Category(String value) {
		this.value = value;
	}
}
