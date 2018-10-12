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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * For full documentation of the TowerData API visit
 * http://docs.towerdata.com/
 * The personalization API's terms and conditions 
 * are stated at http://intelligence.towerdata.com/terms_and_conditions
 */
public class TowerDataApi {
  protected String apiKey;
  protected final static String DIRECT_URL = "https://api.towerdata.com/v5/td";
  protected final static String BULK_URL = "https://api.towerdata.com/v5/ei/bulk";
  protected final static String EMAIL_VALIDATION_URL = "https://api.towerdata.com/v5/ev";
  protected final static String EPPEND_URL = "https://api.towerdata.com/v5/eppend";
  /**
   * Default client-side timeout in milliseconds to wait for a response from the API.
   * This default is overridden if a custom value is given in a constructor.
   * Functions bulkQuery() and validateEmail() do not use this default.
   */
  protected final static int DEFAULT_TIMEOUT = 2000;
  /**
   * Default client-side timeout in milliseconds to wait for a response from the API
   * when calling bulkQuery().
   * This default is overridden if a custom value is given in a constructor.
   */
  protected final static int DEFAULT_BULK_TIMEOUT = 30000;
  /**
   * Default client-side timeout in milliseconds to wait for a response from the API
   * when calling validateEmail().
   * This default is overridden if a positive server-side timeoutSeconds parameter is given to validateEmail().
   */
  protected final static int DEFAULT_EMAIL_VALIDATION_TIMEOUT = 11000;
  
  protected final int timeout;
  protected final int bulkTimeout;
  
  /**
   * Constructor for TowerDataApi class
   * Used to access query member functions
   * @param apiKey    String given by individual's API key
   * The default timeout is set to 2000 ms
   * The default bulk timeout is set to 30000 ms
   */
  public TowerDataApi(String apiKey) {
    this(apiKey, DEFAULT_TIMEOUT, DEFAULT_BULK_TIMEOUT);
  }
  
  /**
   * Constructor for TowerDataApi class
   * Used to access query member functions
   * @param apiKey    String given by individual's API key
   * @param timeout   Supplied integer (ms) overrides the default timeout
   */
  public TowerDataApi(String apiKey, int timeout) {
    this(apiKey, timeout, DEFAULT_BULK_TIMEOUT);
  }
  
  /**
   * Constructor for TowerDataApi class
   * Used to access query member functions
   * @param apiKey    String given by individual's API key
   * @param timeout   Supplied integer (ms) overrides the default timeout
   * @param bulkTimeout   Supplied integer (ms) overrides the default bulk timeout
   */
  public TowerDataApi(String apiKey, int timeout, int bulkTimeout) {
    this.apiKey = apiKey;
    this.timeout = timeout;
    this.bulkTimeout = bulkTimeout;
  }

  /**
   * @see #validateEmail(String, double)
   */
  public JSONObject validateEmail(String email) throws Exception {
    return validateEmail(email, 0.0);
  }

  /**
   * @param email           The email address to be validated (and optionally corrected)
   * @param timeoutSeconds  Server-side timeout value in seconds; max is 30 (seconds).
   *                        Floating-point numbers (e.g. 4.9, 3.55) are permitted.
   *                        The client-side timeout is derived from this parameter if it is positive,
   *                        DEFAULT_EMAIL_VALIDATION_TIMEOUT is used as client-side timeout otherwise.
   * @return                Returns a JSONObject with the response.
   *                        See <a href="http://docs.towerdata.com/#response-overview">http://docs.towerdata.com/#response-overview</a> for details 
   * @throws Exception      Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject validateEmail(String email, double timeoutSeconds) throws Exception {
    String url = EMAIL_VALIDATION_URL + "?email=" + utf8Encode(email) + "&api_key=" + apiKey;
    int timeoutMillis;
    if (timeoutSeconds > 0.0) {
      timeoutMillis = (int)(timeoutSeconds * 1000) + 1000;
      url += "&timeout=" + timeoutSeconds;
    } else {
      timeoutMillis = DEFAULT_EMAIL_VALIDATION_TIMEOUT;
    }
	return getJsonResponse(url, timeoutMillis);
  }

  /**
   * @param email       String email for query
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByEmail(String email) throws Exception {
    return queryByEmail(email, false);
  }

  /**
   * @param email       String email for query.
   * @param fields      A comma-separated list of the data fields you want returned.
   *                    If your API key is configured for multiple data fields,
   *                    you can specify which ones you want returned.
   *                    You will only be charged for the data you receive.
   * @return            Returns a JSONObject associated with the parameter(s).
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300.
   */
  public JSONObject queryByEmail(String email, String fields) throws Exception {
      String url = DIRECT_URL + "?email=" + utf8Encode(email) + "&fields=" + utf8Encode(fields) + "&api_key=" + apiKey;
      return getJsonResponse(url);
  }

  /**
   * @param email       String email for query
   * @param hash_email  If true, md5 hash the email before sending
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByEmail(String email, boolean hash_email) throws Exception {
    if (hash_email) {
      return queryByMd5(MD5Hex(email.toLowerCase()));
    } else {
      String url = DIRECT_URL + "?email=" + utf8Encode(email) + "&api_key=" + apiKey;
      return getJsonResponse(url);
    }
  }

  /**
   * @param md5Email    Md5 hashed string email for query
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByMd5(String md5Email) throws Exception {
    String url = DIRECT_URL + "?md5_email=" + utf8Encode(md5Email) + "&api_key=" + apiKey;
    return getJsonResponse(url);
  }

  /**
   * @param sha1Email   Sha1 hashed string email for query
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryBySha1(String sha1Email) throws Exception {
    String url = DIRECT_URL + "?sha1_email=" + utf8Encode(sha1Email) + "&api_key=" + apiKey;
    return getJsonResponse(url);
  }

  /**
   * @param first       First name
   * @param last        Last name
   * @param street      Street address
   * @param city        City name
   * @param state       State initials
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByNap(String first, String last, String street, String city, String state) throws Exception {
    return queryByNap(first, last, street, city, state, null);
  }
  
  /**
   * @param first       First name
   * @param last        Last name
   * @param street      Street address
   * @param city        City name
   * @param state       State initials
   * @param email       Email address
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByNap(String first, String last, String street, String city, String state, String email) throws Exception {
    String url;
    if (email != null) {
      url = DIRECT_URL + "?email=" + utf8Encode(email) + "&api_key=" + apiKey +
      "&first=" + utf8Encode(first) + "&last=" + utf8Encode(last) +
      "&street=" + utf8Encode(street) + "&city=" + utf8Encode(city) +
      "&state=" + utf8Encode(state);
    } else {
      url = DIRECT_URL + "?api_key=" + apiKey + "&state=" + utf8Encode(state) +
      "&first=" + utf8Encode(first) + "&last=" + utf8Encode(last) +
      "&street=" + utf8Encode(street) + "&city=" + utf8Encode(city);
    }
    return getJsonResponse(url);
  }

  /**
   * @param first       First name
   * @param last        Last name
   * @param zip4         String containing 5 digit Zipcode + 4 digit extension separated by dash
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByNaz(String first, String last, String zip4) throws Exception {
    return queryByNaz(first, last, zip4, null);
  }

  /**
   * @param first       First name
   * @param last        Last name
   * @param zip4         String containing 5 digit Zipcode + 4 digit extension separated by dash
   * @param email       Email address
   * @return            Returns a JSONObject associated with the parameter(s)
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject queryByNaz(String first, String last, String zip4, String email) throws Exception {
    String url;
    if (email != null) {
      url = DIRECT_URL + "?email=" + utf8Encode(email) + "&api_key=" + apiKey +
      "&first=" + utf8Encode(first) + "&last=" + utf8Encode(last) +
      "&zip4=" + zip4;
    } else {
      url = DIRECT_URL + "?api_key=" + apiKey + "&zip4=" + zip4 +
      "&first=" + utf8Encode(first) + "&last=" + utf8Encode(last);
    }
    return getJsonResponse(url);
  }
  
  /**
   * @see #bulkQuery(List, String)
   */
  public JSONArray bulkQuery(List<Map<String,String>> set) throws Exception {
    return bulkQuery(set, "");
  }
  
  /**
   * @param set
   * @param fields      A comma-separated list of the data fields you want returned.
   *                    If your API key is configured for multiple data fields,
   *                    you can specify which ones you want returned.
   *                    You will only be charged for the data you receive.
   *                    If fields is empty, all data fields configured in your API key will be returned.
   * @return            Returns a JSONArray of the responses
   * @throws Exception
   */
  public JSONArray bulkQuery(List<Map<String,String>> set, String fields) throws Exception {
    String urlStr = BULK_URL + "?api_key=" + apiKey;
    if (fields != null && !fields.isEmpty()) {
      urlStr += "&fields=" + fields;
    }
    return new JSONArray(bulkJsonResponse(urlStr, new JSONArray(set).toString()));
  }

  /**
   * @param first       First name
   * @param last        Last name
   * @param street      Street address
   * @param city        City name
   * @param state       State initials
   * @param zip         Zipcode
   * @return            Returns a JSONObject associated with the parameter(s).
   *                    See <a href="http://docs.towerdata.com/#email-append">http://docs.towerdata.com/#email-append</a> for details.
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject appendEmail(String first, String last, String street, String city, String state, String zip) throws Exception {
    String url  = EPPEND_URL
    		+ "?first=" + utf8Encode(first)
    		+ "&last=" + utf8Encode(last)
    		+ "&street=" + utf8Encode(street)
    		+ "&city=" + utf8Encode(city)
    		+ "&state=" + utf8Encode(state)
    		+ "&zip=" + utf8Encode(zip)
    		+ "&api_key=" + apiKey;
    return getJsonResponse(url);
  }

  /**
   * @param email       Email
   * @return            Returns a JSONObject associated with the parameter(s).
   *                    See <a href="http://docs.towerdata.com/#postal-append">http://docs.towerdata.com/#postal-append</a> for details.
   * @throws Exception  Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  public JSONObject appendPostal(String email) throws Exception {
    String url  = EPPEND_URL + "?email=" + utf8Encode(email) + "&api_key=" + apiKey;
    return getJsonResponse(url);
  }

  protected String bulkJsonResponse(String urlStr, String list) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection handle = (HttpURLConnection) url.openConnection();
    handle.setRequestProperty("User-Agent", getUserAgent());
    handle.setRequestProperty("Content-Type", "application/json");
    handle.setConnectTimeout(timeout);
    handle.setReadTimeout(bulkTimeout);
    handle.setDoOutput(true);
    handle.setRequestMethod("POST");
    OutputStreamWriter wr = new OutputStreamWriter(handle.getOutputStream());
    wr.write(list);
    wr.flush();
    BufferedReader rd = new BufferedReader(new InputStreamReader(handle.getInputStream()));
    String line = rd.readLine();
    StringBuilder sb = new StringBuilder();
    while (line != null) {
      sb.append(line);
      line = rd.readLine();
    }
    wr.close();
    rd.close();
    
    int responseCode = handle.getResponseCode();
    if (responseCode < 200 || responseCode > 299) {
      throw new Exception("Error Code " + responseCode + ": " + sb.toString());
    }
    
    return sb.toString();
  }
  
  protected JSONObject getJsonResponse(String urlStr) throws Exception {
    return getJsonResponse(urlStr, timeout);
  }

  /**
   * @param urlStr         String email built in query with URLEncoded email
   * @param timeoutMillis  Timeout in milliseconds for calling the API
   * @return               Returns a JSONObject hash from fields onto field values
   * @throws Exception     Throws error code on all HTTP statuses outside of 200 <= status < 300
   */
  protected JSONObject getJsonResponse(String urlStr, int timeoutMillis) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection handle = (HttpURLConnection) url.openConnection();
    handle.setRequestProperty("User-Agent", getUserAgent());
    handle.setConnectTimeout(timeoutMillis);
    handle.setReadTimeout(timeoutMillis);
    BufferedReader in = new BufferedReader(new InputStreamReader(handle.getInputStream()));
    String responseBody = in.readLine();
    in.close();
    int responseCode = handle.getResponseCode();
    if (responseCode < 200 || responseCode > 299) {
      throw new Exception("Error Code " + responseCode + ": " + responseBody);
    }
    if(responseBody == null || responseBody.equals("")) {
      responseBody = "{}";
    }
    return new JSONObject(responseBody);
  }

  protected String MD5Hex(String s) {
    String result = null;
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] digest = md5.digest(s.getBytes());
      result = toHex(digest);
    } catch (NoSuchAlgorithmException e) {  }
    return result;
  }

  protected String SHA1Hex(String s) {
    String result = null;
    try {
      MessageDigest sha1 = MessageDigest.getInstance("SHA1");
      byte[] digest = sha1.digest(s.getBytes());
      result = toHex(digest);
    } catch (NoSuchAlgorithmException e) {  }
    return result;
  }

  protected String toHex(byte[] a) {
    StringBuilder sb = new StringBuilder(a.length * 2);
    for (int i = 0; i < a.length; i++) {
        sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
        sb.append(Character.forDigit(a[i] & 0x0f, 16));
    }
    return sb.toString();
  }
  
  protected String getUserAgent() {
    return "TowerDataApi/Java/5.0";
  }
  
  private static String utf8Encode(String s) throws UnsupportedEncodingException {
	  return URLEncoder.encode(s, "UTF-8");
  }
}
