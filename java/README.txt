==================
TowerData API
==================

For general information regarding the personalization API, visit
http://www.towerdata.com/developers/api_docs/personalization/direct.
The personalization API's terms and conditions are stated at
http://www.towerdata.com/developers/api_usage.

There are two API jars located in /java/build. The first one
(towerdata-api-complete) bundles json.jar with the TowerData API.
For the other jar, json.jar is not bundled and can be found in /java/lib.

The API is queried by calling any of the query functions belonging
to the TowerDataApi file. An example, TowerDataExample, is
provided. This example queries TowerData's database for an
e-mail. From the command line, you can compile this class as follows.

  javac -cp <path-to-towerdata-api-complete.jar> TowerDataExample.java

This example class takes two arguments: an api-key and the email to query.
After compiling the class you can run it from the command line as follows.

  java -cp <path-to-towerdata-api-complete.jar> com.towerdata.api.personalization.TowerDataExample <api-key> <email>


