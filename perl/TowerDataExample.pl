#!/usr/bin/perl
# Copyright 2014 TowerData
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

use strict;
use warnings;

use lib '.'; # Set to the directory that contains TowerDataAPI.pm
use TowerDataAPI qw(query_by_email query_by_md5 query_by_sha1 query_by_nap query_by_naz);

  # This example script takes an email as a command line argument 
  # and queries TowerData's database for any data associated with
  # the provided email (unknown fields are left blank) 
  # The hash returned from query_by_email is iterated through
  # and each key/value pair is sent to std out 

my $email = $ARGV[0];
unless ($email and ($email =~ /\@/)) {
  # Check for an email parameter
  die "Usage: $0 <email>\n";
}

my $response = query_by_email($email);
while(my ($k, $v) = each %$response) {
  # The response is a hash reference
  if (ref $v eq ref {}) {
    # If the value is a hash reference, iterate over that
    print "$k:\n";
    foreach my $element (keys %{$v}) {
      print "  $element = $v->{$element}\n";
    }
  } else {
    # The value is a scalar
    print "$k = $v.\n";
  }
}
