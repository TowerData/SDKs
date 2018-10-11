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
	private static TowerDataApi intelligenceApi;
	private static TowerDataApi validationApi;
	private static TowerDataApi emailAppendApi;
	private static TowerDataApi postalAppendApi;

	@BeforeClass
	public static void setupBeforeClass() throws IOException {
		Properties properties = new Properties();
		try (Reader reader = new FileReader("test.properties")) {
			properties.load(reader);
		}
		String intelligenceApiKey = properties.getProperty("intelligence.api.key");
		String validationApiKey = properties.getProperty("validation.api.key");
		String emailAppendApiKey = properties.getProperty("email_append.api.key");
		String postalAppendApiKey = properties.getProperty("postal_append.api.key");
		if (!populated(intelligenceApiKey)) throw new IllegalStateException("intelligence.api.key not set in test.properties");
		if (!populated(validationApiKey)) throw new IllegalStateException("validation.api.key not set in test.properties");
		if (!populated(emailAppendApiKey)) throw new IllegalStateException("email_append.api.key not set in test.properties");
		if (!populated(postalAppendApiKey)) throw new IllegalStateException("postal_append.api.key not set in test.properties");
		intelligenceApi = new TowerDataApi(intelligenceApiKey);
		validationApi = new TowerDataApi(validationApiKey);
		emailAppendApi = new TowerDataApi(emailAppendApiKey);
		postalAppendApi = new TowerDataApi(postalAppendApiKey);
	}

	@Test
	public void validateEmail() throws Exception {
		JSONObject response = validationApi.validateEmail("demo@towerdata.com");
		System.out.println("Validate email:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByEmail() throws Exception {
		JSONObject response = intelligenceApi.queryByEmail("demo@towerdata.com", true);
		System.out.println("Query by email:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByEmailWithFields() throws Exception {
		JSONObject response = intelligenceApi.queryByEmail("demo@towerdata.com", "age,gender,household_income,home_owner_status,marital_status");
		System.out.println("Query by email with fields:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByMd5() throws Exception {
		String md5 = intelligenceApi.MD5Hex("demo@towerdata.com");
		JSONObject response = intelligenceApi.queryByMd5(md5);
		System.out.println("Query by MD5:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryBySha1() throws Exception {
		String sha1 = intelligenceApi.SHA1Hex("demo@towerdata.com");
		JSONObject response = intelligenceApi.queryBySha1(sha1);
		System.out.println("Query by SHA1:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByNap() throws Exception {
		JSONObject response = intelligenceApi.queryByNap("Tower", "Data", "33 Irving Place 3rd Floor, Suite 4048", "New York", "NY", null);
		System.out.println("Query by NAP:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryByNaz() throws Exception {
		JSONObject response = intelligenceApi.queryByNaz("Tower", "Data", "10003", null);
		System.out.println("Query by NAZ:\n" + response);
		assertResponse(response);
	}

	@Test
	public void queryBulk() throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		list.add(element("sample@towerdata.com", "",      "",     "",                                      "",         "",   ""     ));
		list.add(element("",                     "Tower", "Data", "33 Irving Place 3rd Floor, Suite 4048", "New York", "NY", "10003"));
		list.add(element("demo@towerdata.com",   "",      "",     "",                                      "New York", "NY", "10003"));
		JSONArray response = intelligenceApi.bulkQuery(list);
		System.out.println("Query bulk:\n" + response);
		assertNotNull(response);
		assertTrue(response.toString().startsWith("[{") && response.toString().endsWith("}]"));
	}

	@Test
	public void appendEmail() throws Exception {
		String first = "TOWER";
		String last = "DATA";
		String street = "33 Irving Place 3rd Floor, Suite 4048";
		String city = "NEW YORK";
		String state = "NY";
		String zip = "10003";
		JSONObject response = emailAppendApi.appendEmail(first, last, street, city, state, zip);
		System.out.println("Append email:\n" + response);
		assertResponse(response);
	}

	@Test
	public void appendPostal() throws Exception {
		JSONObject response = postalAppendApi.appendPostal("sample@towerdata.com");
		System.out.println("Append postal:\n" + response);
		assertResponse(response);
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
