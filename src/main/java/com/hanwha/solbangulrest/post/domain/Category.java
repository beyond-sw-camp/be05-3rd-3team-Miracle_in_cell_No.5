package com.hanwha.solbangulrest.post.domain;

import lombok.Getter;

@Getter
public enum Category {
	CLAIMS("칭찬"), COMPLIMENT("건의"), FREE("자유");

	private final String value;

	Category(String value) {
		this.value = value;
	}
}
