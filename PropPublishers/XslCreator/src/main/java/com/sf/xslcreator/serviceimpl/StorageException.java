package com.sf.xslcreator.serviceimpl;

import java.io.IOException;

public class StorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageException(String string) {
		super(string);
	}

	public StorageException(String string, IOException e) {
		super(string, e.getCause());
		
	}

}
