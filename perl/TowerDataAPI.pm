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
package TowerDataAPI;

use strict;

use LWP::UserAgent;
use JSON;
use Digest::MD5;
use URI::Escape;

our (@ISA, @EXPORT, @EXPORT_OK);

use Exporter();
@ISA       = qw(Exporter);  # So calling programs can use :: notation
@EXPORT    = qw(query_by_email);          # Don't export anything by default
@EXPORT_OK = qw(query_by_md5 query_by_sha1 query_by_nap query_by_naz);

our $API_KEY = 'SET_ME';      # Set your API key here

my $API_BASE_URL = 'https://api.towerdata.com/v5/td';
our $ua = LWP::UserAgent -> new;
$ua -> timeout(2);
$ua -> agent("TowerDataApi/Perl/1.1");  

sub query_by_email {
  # Takes an email and returns a hash which maps attribute fields onto attributes
  # If the hash_email option is set, then the email will be hashed before it's sent to TowerData
  my $email = $_[0];
  my $hash_email = $_[1] || undef;
  if (defined($hash_email)) {
    my $s = Digest::MD5->new;
    $s->add(lc($email));
    query_by_md5($s->hexdigest);
  } else {
    my $url = "$API_BASE_URL?api_key=" . $API_KEY . '&email=' . uri_escape($email);
    __get_json_response($url);
  }
}

sub query_by_md5 {
  # Takes an email that has already been hashed by md5
  # and returns a hash which maps attribute fields onto attributes
  my $md5_email = $_[0];
  my $url = "$API_BASE_URL?api_key=" . $API_KEY . '&md5_email=' . uri_escape($md5_email);
  __get_json_response($url);
}

sub query_by_sha1 {
  # Takes an email that has already been hashed by sha1
  # and returns a hash which maps attribute fields onto attributes
  my $sha1_email = $_[0];
  my $url = "$API_BASE_URL?api_key=" . $API_KEY . '&sha1_email=' . uri_escape($sha1_email);
  __get_json_response($url);
}

sub query_by_nap {
  # Takes first name, last name, and postal (street, city, and state acronym),
  # and returns a hash which maps attribute fields onto attributes
  # Though not necessary, adding an email increases hit rate
  my $first = uri_escape($_[0]);
  my $last = uri_escape($_[1]);
  my $street = uri_escape($_[2]);
  my $city = uri_escape($_[3]);
  my $state = uri_escape($_[4]);
  my $email = uri_escape($_[5]) || undef;
  my $url;
  if (defined($email)) {
    $url = "$API_BASE_URL?api_key=" . $API_KEY . '&email=' . $email .
    '&first=' . $first . '&last=' . $last . '&street=' . $street . '&city=' . $city . '&state=' . $state;
  } else {
    $url = "$API_BASE_URL?api_key=" . $API_KEY .
    '&first=' . $first . '&last=' . $last . '&street=' . $street . '&city=' . $city . '&state=' . $state;
  }
  __get_json_response($url);
}

sub query_by_naz {
  # Takes first name, last name, and zip4 code (5-digit zip 
  # and 4-digit extension separated by a dash as a string),
  # and returns a hash which maps attribute fields onto attributes
  # Though not necessary, adding an email increases hit rate
  my $first = uri_escape($_[0]);
  my $last = uri_escape($_[1]);
  my $zip4 = $_[2];
  my $email = uri_escape($_[3]) || undef;
  my $url;
  if (defined($email)) {
    $url = "$API_BASE_URL?api_key=" . $API_KEY . '&email=' . $email .
    '&first=' . $first . '&last=' . $last . '&zip4=' . $zip4;
  } else {
    $url = "$API_BASE_URL?api_key=" . $API_KEY .
    '&first=' . $first . '&last=' . $last . '&zip4=' . $zip4;
  }
  __get_json_response($url);
}

sub __get_json_response {
  # Takes a url and returns a hash mapping attribute fields onto attributes
  # Note that an exception is raised in the case that
  # an HTTP response code other than 200 is sent back
  # The error code and error body are put in the exception's message
  my $json_response = $ua->get($_[0]);
  unless ($json_response->is_success) {
    if ($API_KEY eq 'SET_ME') {
      warn "You must set the \$API_KEY variable!\n";
    }
    die 'Error Code: ' . $json_response -> status_line . "\n" .
        'Error Body: ' . $json_response;
  }
  my $json = JSON->new->allow_nonref;
  my $personalization = $json->decode($json_response->content);
}

1;
