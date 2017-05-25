package com.properties.publisher.httphandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.properties.publisher.communicationobjects.CommunicationVo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public final class PropertiesHandler implements HttpHandler {

	private CommunicationVo communicationVo;

	public PropertiesHandler(CommunicationVo communicationVo) {
		this.communicationVo = communicationVo;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String, String> parms = PropertiesHandler.queryToMap(exchange.getRequestURI().getQuery());
		String propKey = parms.get("propKey");
		String value = communicationVo.getProperties().getProperty(propKey);
		exchange.sendResponseHeaders(200, value.length());
		OutputStream os = exchange.getResponseBody();
		os.write(value.getBytes());
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
