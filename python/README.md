TowerData Personalization API - Python
==================

For documentation of the TowerData API, visit
http://docs.towerdata.com/

How to Use
==========

Installation
------------

    git clone https://github.com/TowerData/SDKs.git
    cd SDKs/python
    python setup.py sdist
    pip install dist/TowerDataApi-0.3.0.tar.gz --user -U -r requirements.txt

Usage
-----
 
    from towerDataApi import TowerDataApi
    api = TowerDataApi.TowerDataApi('API_KEY')
    api.query_by_email('info@towerdata.com', False, 'gender,age')
    {u'gender': u'Male', u'age': u'25-34'}

Query Options
-------------
The egg supports several ways to query TowerData's API: email, hashed email (either MD5 or SHA1 hash), name and postal (NAP), or name and ZIP+4 (NAZ).

### validate_email(self, email, timeout = None)

This method queries TowerData's [Email Validation API](http://docs.towerdata.com/#email-validation-introduction) with the specified email.
The timeout option is the server-side timeout value in seconds; floating-point numbers (e.g. 4.9, 3.55) are permitted; max is 30 (seconds).
If timeout is set, the server-side timeout is set to this value, otherwise, the default server-side timeout is used.

If your API key is configured for demographic fields, they will be included in the response if the email is valid.

### query_by_email(self, email, hash_email = False, fields = None)

This method queries TowerData's API with the specified email. 
If the hash_email option is set, then the email will be hashed before it's sent to TowerData.
If the fields option is set, then this comma-separated list specifies the data fields you want returned.
If your API key is configured for multiple data fields, you can specify which ones you want returned.
You will only be charged for the data you receive.

### query_by_md5(self, md5_email)
### query_by_sha1(self, sha1_email)

These methods query TowerData's API with the specified email hashes (either MD5 or SHA1, respectively). 
 
### query_by_nap(self, first, last, street, city, state, email = None)

This method queries TowerData's API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code).
An optional email address may be provided to increase the match rate.

### query_by_naz(self, first, last, zip4, email = None)

This method queries TowerData's API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash.
An optional email address may be provided to increase the match rate.

Contributing
============
If you have suggestions or patches, feel free to email us at
<developer at towerdata dot com>. We look forward to hearing from you!


Contributors
============
Nicole Allard <nicole at rapleaf dot com>
Bojan  Milosavljevic <milboj at gmail dot com>


Note that, on unsuccessful requests, we raise an error. Unsuccessful requests are any requests which send back an http response status outside of the range 200 <= status < 300.

License
=======
* The TowerData Personalization API has separate terms and conditions, which can
  be found at http://intelligence.towerdata.com/terms_and_conditions
* If you send us code, please keep in mind that it will be distributed under
  the same license as the rest of the project.
* This code is licensed under the Apache License which follows...

Copyright 2018 TowerData

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
