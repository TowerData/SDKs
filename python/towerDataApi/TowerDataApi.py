# Copyright 2018 TowerData
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

try:
    from urllib import quote
except ImportError:
    from urllib.parse import quote

from urllib3 import HTTPSConnectionPool # See README for download instructions
import json
import hashlib


def http_response_to_json(data):
    return json.JSONDecoder().decode(data)


class TowerDataApi:
    
    HEADERS = {'User-Agent' : 'TowerDataApi/Python/1.2'}
    POST_HEADERS = HEADERS.copy()
    POST_HEADERS.update({
        'Content-Type': 'application/json'
    })
    BASE_PATH = '/v5/td'
    EV_BASE_PATH = '/v5/ev'
    EPPEND_BASE_PATH = '/v5/eppend'
    BULK_BASE_PATH = '/v5/ei/bulk'
    HOST = 'api.towerdata.com'
    TIMEOUT = 30.0
    
    def __init__(self, api_key):
        self.handle = HTTPSConnectionPool(TowerDataApi.HOST, timeout=TowerDataApi.TIMEOUT)
        self.base_path = TowerDataApi.BASE_PATH + '?api_key=%s' % (api_key)
        self.base_ev_path = TowerDataApi.EV_BASE_PATH + '?api_key=%s' % (api_key)
        self.base_eppend_path = TowerDataApi.EPPEND_BASE_PATH + '?api_key=%s' % (api_key)
        self.base_bulk_path = (TowerDataApi.BULK_BASE_PATH +
                               '?api_key=%s' % (api_key))

    def validate_email(self, email, timeout=None):
        """
        Takes an e-mail and returns a map of validation attributes.
        The timeout option is the server-side timeout value in seconds; floating-point numbers (e.g. 4.9, 3.55) are permitted; max is 30 (seconds).
        If timeout is set, it overrides the default server-side timeout.
        """
        if timeout:
            url = '%s&email=%s&timeout=%.1f' % (self.base_ev_path, quote(email), timeout)
        else:
            url = '%s&email=%s' % (self.base_ev_path, quote(email))
        return self.__do_get_request(url)
    
    def query_by_email(self, email, hash_email=False, fields=None):
        """
        Takes an e-mail and returns a hash which maps attribute fields onto attributes
        If the hash_email option is set, then the email will be hashed before it's sent to TowerData.
        If the fields parameter is set, this comma-separated string specifies the attributes you want returned,
        otherwise, all the attributes configured in your API key are returned.
        You will only be charged for the data you receive.
        """
        if hash_email:
            s = hashlib.sha1()
            s.update(email.lower())
            return self.query_by_sha1(s.hexdigest(), fields)
        if fields:
            url = '%s&email=%s&fields=%s' % (self.base_path, quote(email), quote(fields))
        else:
            url = '%s&email=%s' % (self.base_path, quote(email))
        return self.__do_get_request(url)

    def do_bulk_query(self, data, fields=None):
        """
        Takes a list of e-mails and returns a hash with emails as keys and api information for each email
        The data should be following form: [{'email': 'test@example.com'}, {'email': 'test1@example.com'}]
        If the fields parameter is set, this comma-separated string specifies the attributes you want returned,
        otherwise, all the attributes configured in your API key are returned.
        You will only be charged for the data you receive.
        For more information refer to http://docs.towerdata.com/#bulk-email-intelligence-introduction
        """
        if fields:
            url = '%s&fields=%s' % (self.base_path,  quote(fields))
        else:
            url = self.base_path
        return self.__do_post_request(data)

    def query_by_md5(self, md5_email, fields=None):
        """
        Takes an e-mail that has already been hashed by md5
        and returns a hash which maps attribute fields onto attributes.
        If the fields parameter is set, this comma-separated string specifies the attributes you want returned,
        otherwise, all the attributes configured in your API key are returned.
        You will only be charged for the data you receive.
        """
        if fields:
            url = '%s&md5_email=%s&fields=%s' % (self.base_path, quote(md5_email), quote(fields))
        else:
            url = '%s&md5_email=%s' % (self.base_path, quote(md5_email))
        return self.__do_get_request(url)
    
    def query_by_sha1(self, sha1_email, fields=None):
        """
        Takes an e-mail that has already been hashed by sha1
        and returns a hash which maps attribute fields onto attributes.
        If the fields parameter is set, this comma-separated string specifies the attributes you want returned,
        otherwise, all the attributes configured in your API key are returned.
        You will only be charged for the data you receive.
        """
        if fields:
            url = '%s&sha1_email=%s&fields=%s' % (self.base_path, quote(sha1_email), quote(fields))
        else:
            url = '%s&sha1_email=%s' % (self.base_path, quote(sha1_email))
        return self.__do_get_request(url)
        
    def query_by_nap(self, first, last, street, city, state, email=None):
        """
        Takes first name, last name, and postal (street, city, and state acronym),
        and returns a hash which maps attribute fields onto attributes
        Though not necessary, adding an e-mail increases hit rate.
        """
        url = '%s&first=%s&last=%s&street=%s&city=%s&state=%s' % (
            self.base_path, quote(first), quote(last), 
            quote(street), quote(city), quote(state))
        if email:
            url = '%s&email=%s' % (url, quote(email))
        return self.__do_get_request(url)
    
    def query_by_naz(self, first, last, zip4, email=None):
        """
        Takes first name, last name, and zip4 code (5-digit zip 
        and 4-digit extension separated by a dash as a string),
        and returns a hash which maps attribute fields onto attributes
        Though not necessary, adding an e-mail increases hit rate.
        """
        url = '%s&first=%s&last=%s&zip4=%s' % (
            self.base_path, quote(first), quote(last), zip4)
        if email:
            url = '%s&email=%s' % (url, quote(email))
        return self.__do_get_request(url)

    def append_email(self, first, last, street, city, state, zip):
        """
        Takes first name, last name, and postal (street, city, state acronym, and zip),
        and returns a hash which maps attribute fields onto attributes
        """
        url = '%s&first=%s&last=%s&street=%s&city=%s&state=%s&zip=%s' % (
            self.base_eppend_path, quote(first), quote(last),
            quote(street), quote(city), quote(state), quote(zip))
        return self.__do_get_request(url)

    def append_postal(self, email):
        """
        Takes email and returns a hash with first name, last name, and postal (street, city, state acronym, and zip) attributes.
        """
        url = '%s&email=%s' % (self.base_eppend_path, quote(email))
        return self.__do_get_request(url)

    def __do_get_request(self, path):
        """
        Pre: Path is an extension to personalize.rapleaf.com
        Note that an exception is raised if an HTTP response code
        other than 200 is sent back. In this case, both the error code
        the error code and error body are accessible from the exception raised
        """
        json_response = self.handle.request('GET', path, headers=TowerDataApi.HEADERS)
        if 200 <= json_response.status < 300:
            if json_response.data:
                return http_response_to_json(json_response.data)
            else:
                return {}
        else:
            raise Exception(json_response.status, json_response.data.decode("utf-8"))

    def __do_post_request(self, data):
        """
        Note that an exception is raised if an HTTP response code
        other than 200 is sent back. In this case, both the error code
        the error code and error body are accessible from the exception raised
        """
        json_response = self.handle.request(
            'POST', self.base_bulk_path, headers=TowerDataApi.POST_HEADERS,
            body=json.JSONEncoder().encode(data)
        )
        if 200 <= json_response.status < 300:
            if json_response.data:
                resp = http_response_to_json(json_response.data)
                ret = {}
                for i, tmp in enumerate(data):
                    ret[tmp['email']] = resp[i]
                return ret
            else:
                return {}
        else:
            raise Exception(
                json_response.status, json_response.data.decode("utf-8")
            )
