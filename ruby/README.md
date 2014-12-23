How to Use
==========

Installation
------------
> gem install towerdata_api

This gem depends on the "json" gem.

Usage
-----
    > require 'towerdata_api'
    > api = TowerDataApi::Api.new('my secret API key')
    > h = api.query_by_email('test@rapleaf.com')
    => {"gender"=>"Male", "age"=>"25-34"}

Or using global configuration

    > require 'towerdata_api'
    > TowerDataApi::Configuration.begin do |config|
    >   config.api_key= 'my secret API key'
    >   config.timeout= 10 
    > end
    > api = TowerDataApi::Api.new
    > h = api.query_by_email('test@rapleaf.com')
    => {"gender"=>"Male", "age"=>"25-34"}



Constructor Options
-------------------
You can pass in an options hash to the API constructor, like so:

    > api = TowerData::Api.new('my secret API key', :timeout => 10)

The possible options/keys accepted by the constructor are:

 - :timeout => The max amount of time to wait for a request to finish. Defaults to 2.
 - :ca_file => Set this to your system-wide root CA cert path if you're having SSL verification issues. Defaults to nil.
 
Query Options
-------------
The gem supports several ways to query TowerData's API: email, hashed email (either MD5 or SHA1 hash), name and postal (NAP), or name and ZIP+4 (NAZ).

### query_by_email(email, options)

This method queries Rapleaf's API with the specified email. The options hash accepts the following keys:

 - :hash_email    => Whether to (SHA1) hash the email before querying TowerData's API with it. Defaults to nil.

### query_by_md5(md5_email, options)
### query_by_sha1(sha1_email, options)

### query_by_nap(first, last, street, city, state, options)

This method queries TowerData's API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code). It also accepts the following options hash:

 - :email          => You can include an email in your NAP query to increase the hit rate. Defaults to nil.

### query_by_naz(first, last, zip4, options)

This method queries TowerData's API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash. This method accepts the following options:

 - :email          => You can include an email in your NAP query to increase the hit rate. Defaults to nil.

### get_email_validation(email)

This method queries TowerData's API with email and return email_validation object. Raise error if email_validation is not enabled. 

### valid_email?(email)

This method queries TowerData's API with email and return boolean. Raise error if email_validation is not enabled. 


Contributing
============
If you have suggestions or patches, feel free to email us at
[developer at towerdata dot com]. We look forward to hearing from you!


Contributors
============
 - Greg Poulos [greg at rapleaf dot com]
 - Sean Carr [sean at rapleaf dot com]
 - Vlad Shulman [vlad at rapleaf dot com]
 - Bojan Milosavljevic [milboj at gmail dot com]
