package com.properties.publisher.httphandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.properties.publisher.communicationobjects.CommunicationVo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public final class InfoHandler implements HttpHandler {

	private CommunicationVo communicationVo;

	public InfoHandler(CommunicationVo communicationVo) {
		this.communicationVo = communicationVo;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Set<Object> keys = communicationVo.getProperties().keySet();
		StringBuffer stbuf = new StringBuffer();
		for (Object k : keys) {
			String key = (String) k;
			stbuf.append(key);
			stbuf.append("\t=\t");
			stbuf.append(communicationVo.getProperties().getProperty(key));
			stbuf.append("\n");
		}
		exchange.sendResponseHeaders(200, stbuf.toString().length());
		OutputStream os = exchange.getResponseBody();
		os.write(stbuf.toString().getBytes());
		os.close();

	}

	/**
	 * returns the url parameters in a map
	 * 
	 * @param query
	 * @return map
	 */
	public static Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

}
