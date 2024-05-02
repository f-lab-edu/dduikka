package com.flab.dduikka.domain.helper;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONFileReader {
	public static <T> T readJSONFile(String path, Class<T> valueType) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = JSONFileReader.class.getResourceAsStream(path);
		JsonNode jsonNode = mapper.readTree(stream);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.treeToValue(jsonNode, valueType);
	}
}
