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
  include "TowerDataApi.php";
  
  /* This example script takes an e-mail as a command line argument 
   * and queries TowerData's service for any data associated with
   * the provided e-mail (unknown fields are left blank) 
   * The hash returned from query_by_email is iterated through
   * and each key/value pair is sent to std out 
   */
  
  $person = $argv[1];
  $response = query_by_email($person, false);
  print_r($response);
?>
