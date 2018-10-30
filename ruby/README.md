TowerData Personalization API - Ruby
====================================

For documentation of the TowerData API, visit 
http://docs.towerdata.com/

How to Use
==========

Installation
------------
> gem install towerdata_api

This gem depends on the "json" gem.

Usage
-----
    require 'towerdata_api'
    api = TowerDataApi::Api.new('my secret API key')
    h = api.query_by_email('info@towerdata.com', {:fields => 'age,gender,household_income,home_owner_status,marital_status'})
    => {"household_income":"25k-35k","home_owner_status":"Own","age":"65+","gender":"Male","marital_status":"Married"}

Or using global configuration

    require 'towerdata_api'
    TowerDataApi::Configuration.begin do |config|
      config.api_key= 'my secret API key'
      config.timeout= 10 
    end
    api = TowerDataApi::Api.new
    h = api.query_by_email('info@towerdata.com', {:fields => 'age,gender,household_income,home_owner_status,marital_status'})
    => {"household_income":"25k-35k","home_owner_status":"Own","age":"65+","gender":"Male","marital_status":"Married"}

API Keys
--------

To obtain a free TowerData API key for testing:

1. Register at https://instantdata.towerdata.com/
2. Click on the "Check Out Our API" button or the API tab in the header
3. Click on "Get API Key" button

Your API key will be initially configured for Email Validation and several
demographic fields from our Email Intelligence service.

TowerData API keys with Email Validation must use the [validation
api endpoint](http://docs.towerdata.com/#validation-api-endpoint).

Because of this, use the validate_email method described below for
your initial tests. [Contact us](https://www.towerdata.com/contact-towerdata)
if you would like to change the services or data fields your API key
is configured for.

Constructor Options
-------------------
You can pass in an options hash to the API constructor, like so:

    api = TowerData::Api.new('my secret API key', :timeout => 10)

The possible options/keys accepted by the constructor are:

 - :timeout => The max amount of time to wait for a request to finish. Defaults to 2.
 - :bulk_timeout => The max amount of time to wait for a bulk request to finish. Defaults to 30.
 - :val_timeout => The max amount of time to wait for a validation request to finish. Defaults to 11.
 - :ca_file => Set this to your system-wide root CA cert path if you're having SSL verification issues. Defaults to nil.
 
Query Options
-------------
The gem supports several ways to query TowerData's API: email, hashed email (either MD5 or SHA1 hash), name and postal (NAP), or name and ZIP+4 (NAZ).

### validate_email(email, timeout = 0)

This method queries TowerData's [Email Validation API](http://docs.towerdata.com/#email-validation-introduction) with the specified email.
The optional second parameter is the timeout in seconds; the API's default value is used if timeout is not positive.

If your API key is configured for demographic fields, they will be included in the response if the email is valid.

### query_by_email(email, options)

This method queries TowerData's API with the specified email. The options hash accepts the following keys:

 - :hash_email    => Whether to (SHA1) hash the email before querying TowerData's API with it. Defaults to nil.
 - :fields        => A comma-separated list of the data fields you want returned. If your API key is configured for multiple data fields, you can specify which ones you want returned. You will only be charged for the data you receive.

### query_by_md5(md5_email, options)
### query_by_sha1(sha1_email, options)

These methods query TowerData's API with the hashed emails provided to them (either MD5 or SHA1, respectively). 

### query_by_nap(first, last, street, city, state, options)

This method queries TowerData's API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code). It also accepts the following options hash:

 - :email          => You can include an email in your NAP query to increase the hit rate. Defaults to nil.
 - :fields         => A comma-separated list of the data fields you want returned. If your API key is configured for multiple data fields, you can specify which ones you want returned. You will only be charged for the data you receive.

### query_by_naz(first, last, zip4, options)

This method queries TowerData's API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash. This method accepts the following options:

 - :email          => You can include an email in your NAP query to increase the hit rate. Defaults to nil.
 - :fields         => A comma-separated list of the data fields you want returned. If your API key is configured for multiple data fields, you can specify which ones you want returned. You will only be charged for the data you receive.

### append_email(first, last, street, city, state, zip)

Takes first name, last name, street, city, and zip code, and returns the matched email.

### append_postal(email)

Takes an email and returns the matched postal data.


Contributing
============
If you have suggestions or patches, feel free to email us at
[developer at towerdata dot com]. We look forward to hearing from you!

License
=======
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
