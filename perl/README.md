How to Use
==========

Installation
------------
The TowerDataAPI.pm package file should be in the same directory as your script,
or set your script to include the directory with the package file.

> use lib '/path/to/directory/with/dot_pm_file';

At the top of TowerDataAPI.pm, update the $API_KEY variable with the API
key you received from TowerData.

Usage
-----
    > use TowerDataAPI;
    > my $response = query_by_email('pete@rapleafdemo.com');
    > print "Gender is $response->{'gender'}\n";
    => Gender is Male

    > my $response = query_by_nap('Pete', 'Schlick', '112134 Leavenworth Rd.',
                                  'San Francisco', 'CA');
    > print "Age is $response->{'age'}\n";
    => Gender is 25-34
 
API Functions
-------------
The Perl SDK supports several ways to query TowerData's API: email, hashed email (either MD5 or SHA1 hash), name and postal (NAP), or name and ZIP+4 (NAZ).

### query_by_email($email, $options)

This method queries TowerData's API with the specified email. If $options is set to true, the email address will be hashed to MD5 before querying TowerData's API.

### query_by_md5($md5_email)
### query_by_sha1($sha1_email)

These methods query TowerData's API with either the MD5 or the SHA1 of an email. You must hash the email prior to calling the functions using lower case and with white space removed.

### query_by_nap($first, $last, $street, $city, $state, $optional_email)

This method queries TowerData's API with a name and postal address: first name, last name, street, city, and state acronym (i.e., the state's 2-character postal code). It also accepts an optional email address, which increases the match rate.

### query_by_naz(first, last, zip4, options)

This method queries TowerData's API with a name and ZIP+4 code. The ZIP+4 is a string with a 5-digit ZIP code and 4-digit extension separated by a dash. It also accepts an optional email address, which increases the match rate.

Contributing
============
If you have suggestions or patches, feel free to email us at
[developer at towerdata dot com]. We look forward to hearing from you!


Contributors
============
 - Greg Poulos [greg at rapleaf dot com]
 - Sean Carr [sean at rapleaf dot com]
 - Vlad Shulman [vlad at rapleaf dot com]
