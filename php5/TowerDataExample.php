<?php
 /*
  * Copyright 2010 Rapleaf
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
  include "TowerDataApi.php";
  
 /* This example script can be run on the command line with e-mail
  * as a command-line argument, or it can be run on a PHP server
  * with "?email=<e-mail>" specified in the URL.
  * It queries TowerData's database for any data associated with
  * the provided e-mail (unknown fields are left blank) 
  * The hash returned from query_by_email is iterated through
  * and the resulting data structure is printed to STDOUT or to
  * the browser.
  */
  if (defined('STDIN')) {
    $person = $argv[1];
  } else {
    echo '<html><body>';
    $person = $_GET['email'];
  }
  $api = new TowerDataApi('PUT YOUR API KEY HERE');
  try {
    $response = $api -> query_by_email($person, $hash_email = false);
    if (defined('STDIN')) {
      print_r($response);
    } else {
      echo '<pre>';
      print_r($response);
      echo '</pre><br>';
    }
  } catch (\Exception $e) {
    echo 'Caught exception: ' .  $e->getMessage() . "\n";
  }

  if (defined('STDIN')) {
    echo '</body></html>';
  }
?>
