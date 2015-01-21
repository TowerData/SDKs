package com.towerdata.api.personalization;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestTowerDataApi {
  String apiKey;
  String email;
  String first;
  String last;
  String street;
  String city;
  String state;
  String zip;

  @Before
  public void getTestVariables() {
    apiKey = System.getenv("api_key");
    email = System.getenv("email");
    first = System.getenv("first");
    last = System.getenv("last");
    street = System.getenv("street");
    city = System.getenv("city");
    state = System.getenv("state");
    zip = System.getenv("zip");
  }

  @Test
  public void queryByEmail() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);        
    JSONObject response = null;

    try {
      response = api.queryByEmail(email, true);
      System.out.println("\nQuery by email: \n" + response);
      Assert.assertTrue(response.toString().startsWith("{") && response.toString().endsWith("}"));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }

  @Test
  public void queryByMd5() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);        
    JSONObject response = null;

    try {
      response = api.queryByMd5(api.MD5Hex(email));
      System.out.println("\nQuery by MD5: \n" + response);
      Assert.assertTrue(response.toString().startsWith("{") && response.toString().endsWith("}"));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }

  @Test
  public void queryBySha1() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);        
    JSONObject response = null;

    try {
      response = api.queryBySha1(api.SHA1Hex(email));
      System.out.println("\nQuery by SHA1: \n" + response);
      Assert.assertTrue(response.toString().startsWith("{") && response.toString().endsWith("}"));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }

  @Test
  public void queryByNap() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);        
    JSONObject response = null;

    try {
      response = api.queryByNap(first, last, street, city, state, email);
      System.out.println("\nQuery by NAP: \n" + response);
      Assert.assertTrue(response.toString().startsWith("{") && response.toString().endsWith("}"));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }

  @Test
  public void queryByNaz() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);        
    JSONObject response = null;

    try {
      response = api.queryByNaz(first, last, zip, email);
      System.out.println("\nQuery by NAZ: \n" + response);
      Assert.assertTrue(response.toString().startsWith("{") && response.toString().endsWith("}"));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }

  @Test
  public void queryBulk() throws Exception {
    TowerDataApi api = new TowerDataApi(apiKey);
    JSONArray response = null;
    final List<Map<String,String>> list = new ArrayList<Map<String, String>>() {
      {
        add(new HashMap<String, String>(){
          {
            put("email", "pete@rapleafdemo.com");
          }
        });
        add(new HashMap<String, String>(){
          {
            put("first", "Peter");
            put("last", "Schlick");
            put("street", "112134 Leavenworth Rd.");
            put("city", "San Francisco");
            put("state", "CA");
            put("zip", "94109");
          }
        });
        add(new HashMap<String, String>(){
          {
            put("email", "baller@gmail.com");
            put("zip", "21044");
            put("city", "Columbia");
            put("state", "Maryland");
          }
        });
      }
    };

    try {
      response = api.bulkQuery(list);
      Assert.assertTrue(response.toString().startsWith("[{") && response.toString().endsWith("}]"));
      System.out.println("\nQuery bulk: \n" + response);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertFalse(response == null);
    }
  }
}