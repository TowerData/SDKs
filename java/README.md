TowerData API - Java
====================================

For documentation of the TowerData API, visit 
http://docs.towerdata.com/

There are two API jars located in /java/build. The first one
(towerdata-api-complete) bundles json.jar with the TowerData API.
For the other jar, json.jar is not bundled and can be found in /java/lib.

Example
-------

The API is queried by calling any of the query functions belonging
to the TowerDataApi file. An example, TowerDataExample, is
provided. This example queries the TowerData API for an
email. From the command line, you can compile this class as follows.

      javac -cp <path-to-towerdata-api-complete.jar> TowerDataExample.java

This example class takes two arguments: an api-key and the email to query.
After compiling the class you can run it from the command line as follows.

      java -cp <path-to-towerdata-api-complete.jar> com.towerdata.api.personalization.TowerDataExample <api-key> <email>

API Keys
--------

To obtain a free TowerData API key for testing:

1. Register at https://instantdata.towerdata.com/
2. Click on the "Check Out Our API" button or the API tab in the header
3. Click on "Get API Key" button

Your API key will be initially configured for Email Validation and several 
demographic fields from our Email Intelligence service. 

TowerData API keys with Email Validation must use the [validation
api endpoint](http://docs.towerdata.com/#validation-api-endpoint)

Because of this, use the validateEmail method described below for
your initial tests. [Contact us](https://www.towerdata.com/contact-towerdata)
if you would like to change the services or data fields your API key
is configured for.

Query Options
-------------
The SDK supports several ways to query TowerData's APIs.

### validateEmail(String email, double timeoutSeconds)

This method queries TowerData's Email Validation API with the specified email.
The second parameter is the timeout in seconds; the API's default value is used if timeoutSeconds is not positive.

If your API key is configured for demographic fields, they will be returned if the email is valid.

### queryByEmail(String email, boolean hash_email)

This method queries TowerData's Email Intelligence API with the specified email. If the second parameter is set to true, the email address will be hashed to an MD5 before querying the API with it. Defaults to false.

This method will not work if your API key is configured for Email Validation. The validateEmail method should be used in that case.

### queryByMd5(String md5Email)
### queryBySha1(String sha1Email)

These methods query TowerData's Email Intelligence API with the hashed emails provided to them (either MD5 or SHA1, respectively). 

### queryByNap(String first, String last, String street, String city, String state, String email)

This method queries TowerData's Email Intelligence API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code). It also accepts an optional email address, which can increase the match rate.

### queryByNaz(String first, String last, String zip4, String email)

This method queries TowerData's Email Intelligence API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash. It also accepts an optional email address, which can increase the match rate.

### bulkQuery(set)

Queries the TowerData Email Intelligence API with an HashMap of emails or names and postal addresses and returns a JSONarray containing information on each input identifier.

### appendEmail(String first, String last, String street, String city, String state, String zip)

This method queries TowerData's Email Append API with the specified name and postal address.

License
-------
Copyright 2018 TowerData

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
