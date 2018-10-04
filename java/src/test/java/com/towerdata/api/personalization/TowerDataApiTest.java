package com.towerdata.api.personalization;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class TowerDataApiTest {
	private static TowerDataApi tdApi;
	private static TowerDataApi evApi;

	@BeforeClass
	public static void setupBeforeClass() throws IOException {
		Properties properties = new Properties();
		try (Reader reader = new FileReader("test.properties")) {
			properties.load(reader);
		}
		String tdApiKey = properties.getProperty("td.api.key");
		String evApiKey = properties.getProperty("ev.api.key");
		if (!populated(tdApiKey)) throw new IllegalStateException("td.api.key not set in test.properties");
		if (!populated(evApiKey)) throw new IllegalStateException("ev.api.key not set in test.properties");
		tdApi = new TowerDataApi(tdApiKey);
		evApi = new TowerDataApi(evApiKey);
	}

	@Test
	public void validateEmail() throws Exception {
		JSONObject response = evApi.validateEmail("baller@gmail.com");
		System.out.println("Validate email:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByEmail() throws Exception {
		JSONObject response = tdApi.queryByEmail("baller@gmail.com", true);
		System.out.println("Query by email:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByMd5() throws Exception {
		String md5 = tdApi.MD5Hex("baller@gmail.com");
		JSONObject response = tdApi.queryByMd5(md5);
		System.out.println("Query by MD5:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryBySha1() throws Exception {
		String sha1 = tdApi.SHA1Hex("baller@gmail.com");
		JSONObject response = tdApi.queryBySha1(sha1);
		System.out.println("Query by SHA1:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByNap() throws Exception {
		JSONObject response = tdApi.queryByNap("Peter", "Schlick", "112134 Leavenworth Rd.", "San Francisco", "CA", "");
		System.out.println("Query by NAP:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByNaz() throws Exception {
		JSONObject response = tdApi.queryByNaz("Peter", "Schlick", "94109", "");
		System.out.println("Query by NAZ:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryBulk() throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		list.add(element("pete@rapleafdemo.com",	"", 		"",			"",							"",					"",			""));
		list.add(element("",						"Peter",	"Schlick",	"112134 Leavenworth Rd.",	"San Francisco",	"CA",		"94109"));
		list.add(element("baller@gmail.com",		"",			"",			"",							"Columbia",			"Maryland",	"21044"));
		JSONArray response = tdApi.bulkQuery(list);
		System.out.println("Query bulk:\n" + response);
		assertNotNull(response);
		assertTrue(response.toString().startsWith("[{") && response.toString().endsWith("}]"));
	}
	
	private static void assertResponse(JSONObject response) {
		assertNotNull(response);
		String responseString = response.toString();
		assertTrue(responseString.startsWith("{") && responseString.endsWith("}"));
	}

	private static Map<String, String> element(String email, String first, String last, String street, String city, String state, String zip) {
		Map<String, String> map = new HashMap<>();
		if (populated(email)) map.put("email", email);
		if (populated(first)) map.put("first", email);
		if (populated(last)) map.put("last", last);
		if (populated(street)) map.put("street", street);
		if (populated(city)) map.put("city", city);
		if (populated(state)) map.put("state", state);
		if (populated(zip)) map.put("zip", zip);
		return map;
	}

	private static boolean populated(String s) {
		return s != null && !s.isEmpty();
	}
}
