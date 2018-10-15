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
		if (args.length < 1) {
			throw new IllegalArgumentException("API key required as first command-line argument.");
		}

		String apiKey = args[0];
		TowerDataApi api = new TowerDataApi(apiKey);

		// Query by email given in command-line argument
		try {
			String email = args.length < 2 ? "info@towerdata.com" : args[1];
			String fields = "age,gender,household_income,home_owner_status,marital_status";
			JSONObject response = api.queryByEmail(email, fields);
			System.out.println("Query by email:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Email Validation - valid example
		try {
			String email = "info@towerdata.com";
			JSONObject response = api.validateEmail(email);
			System.out.println("\nValidate email - valid example:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// Email Validation - correction example
		try {
			String email = "info@@towerdata,com";
			JSONObject response = api.validateEmail(email);
			System.out.println("\nValidate email - correction example:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Query by email example
		try {
			String email = "info@towerdata.com";
			String fields = "age,gender,household_income,home_owner_status,marital_status";
			JSONObject response = api.queryByEmail(email, fields);
			System.out.println("\nQuery by email example:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bulk Query example
		try {
			Map<String, String> map1 = new HashMap<>();
			map1.put("email", "info@towerdata.com");

			Map<String, String> map2 = new HashMap<>();
			map2.put("first", "Tower");
			map2.put("last", "Data");
			map2.put("street", "33 Irving Place 3rd Floor, Suite 4048");
			map2.put("city", "New York");
			map2.put("state", "NY");
			map2.put("zip", "10003");

			List<Map<String, String>> list = new ArrayList<>();
			list.add(map1);
			list.add(map2);
			String fields = "age,gender,household_income,home_owner_status,marital_status";
			JSONArray response = api.bulkQuery(list, fields);
			System.out.println("\nBulk Query example:\n" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
