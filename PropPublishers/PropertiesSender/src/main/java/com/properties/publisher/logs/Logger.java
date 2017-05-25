package com.properties.publisher.logs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	public static final String LOG_FILE = "log.txt";

	public static void log(Object message) {
		if (message == null) {
			return;
		}

		writeLogFile(message);
	}

	private static void writeLogFile(Object message) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(LOG_FILE, true);
			writer.append(new Date().toString());
			writer.append(":");
			writer.append(message.toString());
			writer.append("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
			}
		}
	}
}
