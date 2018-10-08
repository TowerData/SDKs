package com.towerdata.api.personalization;
/*
 * Copyright 2018 TowerData
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class TowerDataApiExample {
	public static void main(String[] args) {
		TowerDataApi api = (args.length >= 1 && args[0] != null) ? new TowerDataApi(args[0]) : new TowerDataApi("YOUR_API_KEY");
		String email = (args.length >= 2 && args[1] != null) ? args[1] : "demo@towerdata.com";

		// Validate Email
		try {
			JSONObject response = api.validateEmail(email);
			System.out.println("Validate email:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bulk Query
		List<Map<String, String>> list = new ArrayList<>();
		list.add(element(email, "", "", "", "", "", ""));
		list.add(element("demo@towerdata.com", "", "", "", "", "", ""));
		list.add(element("", "Tower", "Data", "33 Irving Place 3rd Floor, Suite 4048", "New York", "NY", "10003"));

		try {
			JSONArray response = api.bulkQuery(list);
			System.out.println("\nBulk Query:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
