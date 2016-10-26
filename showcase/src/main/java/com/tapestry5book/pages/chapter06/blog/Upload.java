package com.tapestry5book.pages.chapter06.blog;

import java.io.File;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

public class Upload {

	@Property
	private UploadedFile file;

	public void onSuccess() {

		String tempDir = System.getProperty("java.io.tmpdir");
		// System.out.println("==============" + tempDir);
		File targetFile = new File(tempDir, file.getFileName());

		file.write(targetFile);
	}

	// Handling upload failures
	@Property
	private String uploadErrorMessage;

	Object onUploadException(FileUploadException ex) {
		uploadErrorMessage = "Upload exception: " + ex.getMessage();
		return this;
	}

}