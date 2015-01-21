<?php
  /*
  * Copyright 2014 TowerData
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

  namespace TowerData;
  class TowerDataApi {
    private static $BASE_PATH = "https://api.towerdata.com/v5/td?api_key=";
    private static $BULK_PATH = "https://api.towerdata.com/v5/ei/bulk?api_key=";
    private static $handle;
    private static $API_KEY;
  
    /* Note that an exception is raised in the case that
     * an HTTP response code other than 200 is sent back
     * The error code and error body are displayed
     */
  
    function __construct($api_key) {
      self::$handle = curl_init();
      self::$API_KEY = $api_key;
      curl_setopt(self::$handle, CURLOPT_RETURNTRANSFER, TRUE);
      curl_setopt(self::$handle, CURLOPT_SSL_VERIFYPEER, TRUE);
      curl_setopt(self::$handle, CURLOPT_USERAGENT, "TowerDataApi/PHP5/1.1");
    }
  
    function query_by_email($email, $hash_email = false) {
      /* Takes an email and returns a hash which maps attribute fields onto attributes
       * If the hash_email option is set, then the email will be hashed before it's sent to TowerData
       */
      if ($hash_email) {
        $md5_email = sha1(strtolower($email));
        return self::query_by_md5($sha1_email);
      } else {
        $url = self::$BASE_PATH . self::$API_KEY . "&email=" . urlencode($email);

        return self::get_json_response($url);
      }
    }
    
    function query_by_md5($md5_email) {
      /* Takes an email that has already been hashed by md5
       * returns a hash which maps attribute fields onto attributes
       */
      $url = self::$BASE_PATH . self::$API_KEY . "&md5_email=" . urlencode($md5_email);
      return self::get_json_response($url);
    }
    
    function query_by_sha1($sha1_email) {
      /* Takes an email that has already been hashed by sha1
       * and returns a hash which maps attribute fields onto attributes
       */
      $url = self::$BASE_PATH . self::$API_KEY . "&sha1_email=" . urlencode($sha1_email);
      return self::get_json_response($url);
    }
    
    function query_by_nap($first, $last, $street, $city, $state, $email = null) {
      /* Takes first name, last name, and postal (street, city, and state acronym),
       * and returns a hash which maps attribute fields onto attributes
       * Though not necessary, adding an email increases hit rate
       */
      if ($email) {
        $url = self::$BASE_PATH . self::$API_KEY . "&email=" . urlencode($email) .
        "&first=" . urlencode($first) . "&last=" . urlencode($last) . 
        "&street=" . urlencode($street) . "&city=" . urlencode($city) . "&state=" . urlencode($state);
      } else {
        $url = self::$BASE_PATH . self::$API_KEY . "&first=" . urlencode($first) . "&last=" . urlencode($last) . 
        "&street=" . urlencode($street) . "&city=" . urlencode($city) . "&state=" . urlencode($state);
      }
      return self::get_json_response($url);
    }
    
    function query_by_naz($first, $last, $zip4, $email = null) {
      /* Takes first name, last name, and zip4 code (5-digit zip 
       * and 4-digit extension separated by a dash as a string),
       * and returns a hash which maps attribute fields onto attributes
       * Though not necessary, adding an email increases hit rate
       */
      if ($email) {
        $url = self::$BASE_PATH . self::$API_KEY . "&email=" . urlencode($email) .
        "&first=" . urlencode($first) . "&last=" . urlencode($last) . "&zip4=" . $zip4;
      } else {
        $url = self::$BASE_PATH . self::$API_KEY . "&zip4=" . $zip4 .
        "&first=" . urlencode($first) . "&last=" . urlencode($last);
      }
      return self::get_json_response($url);
    }

    function bulk_query($set) {
      $url = self::$BULK_PATH . self::$API_KEY;
      return self::get_bulk_query(json_encode($set), $url); 
    }

    private function get_bulk_query($data, $url) {
      curl_setopt(self::$handle, CURLOPT_HTTPHEADER, array("Content-Type: application/json"));
      curl_setopt(self::$handle, CURLOPT_URL, $url);
      curl_setopt(self::$handle, CURLOPT_TIMEOUT, 30.0);
      curl_setopt(self::$handle, CURLOPT_POST, true);
      curl_setopt(self::$handle, CURLOPT_POSTFIELDS, $data);
      $json_string = curl_exec(self::$handle);
      if ( ! $json_string ) {
        $json_string = curl_exec(self::$handle);
      }
      $response_code = curl_getinfo(self::$handle, CURLINFO_HTTP_CODE);
      if ($response_code < 200 || $response_code >= 300) {
        throw new \Exception("Error Code: " . $response_code . "\nError Body: " . $json_string);
      } else {
        $personalization = json_decode($json_string, TRUE);
        return $personalization;
      }

    }
  
    private function get_json_response($url) {
      /* Pre: Path is an extension to api.towerdata.com
       * Note that an exception is raised if an HTTP response code
       * other than 200 is sent back. In this case, both the error code
       * the error code and error body are accessible from the exception raised
       */

      curl_setopt(self::$handle, CURLOPT_URL, $url);
      curl_setopt(self::$handle, CURLOPT_TIMEOUT, 2.0);
      $json_string = curl_exec(self::$handle);
      $response_code = curl_getinfo(self::$handle, CURLINFO_HTTP_CODE);
      if ($response_code < 200 || $response_code >= 300) {
        throw new \Exception("Error Code: " . $response_code . "\nError Body: " . $json_string);
      } else {
        $personalization = json_decode($json_string, TRUE);
        return $personalization;
      }
    }
  }
?>
