package com.sf.xslcreator.serviceimpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

import com.properties.receiver.annotation.PropertyScan;
import com.properties.receiver.annotation.SetProperty;
import com.sf.xslcreator.service.StorageService;

@PropertyScan
@ComponentScan
public class FileSystemStorageService implements StorageService {

	@SetProperty(property = "/public/authenticateUser")
	private String filePath;

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void store(MultipartFile file) throws StorageException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String readFile(MultipartFile file) throws StorageException {
		StringBuffer fileContent = new StringBuffer();
		return fileContent.toString();
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
