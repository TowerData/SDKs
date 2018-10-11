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
		// Set your API key(s) here
		String validationApiKey = "";
		String intelligenceApiKey = "";

		// Email Validation
		if (!validationApiKey.isEmpty()) {
			TowerDataApi api = new TowerDataApi(validationApiKey);
	
			// valid
			String email = "info@towerdata.com";
			try {
				JSONObject response = api.validateEmail(email);
				System.out.println("\nValidate email:\n" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			// invalid with correction
			email = "info@@towerdata,com";
			try {
				JSONObject response = api.validateEmail(email);
				System.out.println("\nValidate email:\n" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Email Intelligence
		if (!intelligenceApiKey.isEmpty()) {
			TowerDataApi api = new TowerDataApi(intelligenceApiKey);

			// Query by email - return all fields
			String email = "info@towerdata.com";
			try {
				JSONObject response = api.queryByEmail(email);
				System.out.println("\nQuery by email - all fields:\n" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Query by email - limit fields
			String fields = "age,gender,household_income,home_owner_status,marital_status";
			try {
				JSONObject response = api.queryByEmail(email, fields);
				System.out.println("\nQuery by email - limit fields:\n" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Bulk Query
			List<Map<String, String>> list = new ArrayList<>();
	
			Map<String, String> map1 = new HashMap<>();
			map1.put("email", "info@towerdata.com");
			list.add(map1);
	
			Map<String, String> map2 = new HashMap<>();
			map2.put("first", "Tower");
			map2.put("last", "Data");
			map2.put("street", "33 Irving Place 3rd Floor, Suite 4048");
			map2.put("city", "New York");
			map2.put("state", "NY");
			map2.put("zip", "10003");
			list.add(map2);
	
			try {
				JSONArray response = api.bulkQuery(list);
				System.out.println("\nBulk Query:\n" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
