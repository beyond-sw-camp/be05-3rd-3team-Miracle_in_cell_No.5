package com.hanwha.solbangulrest.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadFile {

	private String originalFilename;
	private String storeFilename;
}
