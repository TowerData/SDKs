TowerData Personalization API - PHP 4
=====================================

For documentation of TowerData's personalization API, visit 
http://intelligence.towerdata.com/developers/personalization-api/personalization-api-documentation

The API is queried by calling any of the query functions belonging to the TowerDataApi file. An example script, TowerDataExample, is provided. The example script takes an email as a command line parameter, connects to the TowerData service, and returns (and sends to stdout) a collection of associated key-value pairs.

This sample code is for use with PHP 4. There is a separate Software Development Kit (SDK) for PHP 5.

You must set your TowerData API key in TowerDataApi.php prior to using this SDK.

Example
-------

      namespace TowerData;
      include "TowerDataApi.php";
      
      $person = $argv[1];
      $response = query_by_email($person, false);
      print_r($response);

Query Options
-------------
The SDK supports several ways to query TowerData's API: email, hashed email (either MD5 or SHA1 hash), name and postal (NAP), or name and ZIP+4 (NAZ).

### query_by_email($email, $hash_email = false)

This method queries TowerData's API with the specified email. If the second parameter is set to true, the email address will be hashed to an MD5 before querying TowerData's API with it. Defaults to false.

### query_by_md5($md5_email)
### query_by_sha1($sha1_email)

These methods query TowerData's API with the hashed emails provided to them (either MD5 or SHA1, respectively). 

### query_by_nap($first, $last, $street, $city, $state, $email = null)

This method queries TowerData's API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code). It also accepts an optional email address, which can increase the match rate.

### query_by_naz($first, $last, $zip4, $email = null)

This method queries TowerData's API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash. It also accepts an optional email address, which can increase the match rate.

License
-------
Copyright 2014 TowerData

* The TowerData Personalization API has separate terms and conditions, which can
  be found at http://intelligence.towerdata.com/terms_and_conditions
* If you send us code, please keep in mind that it will be distributed under
  the same license as the rest of the project.
* This code is licensed under the Apache License which follows...

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
