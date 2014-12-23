require 'spec_helper'
describe 'api_calls' do
  it 'query without configuration' do
    api = TowerDataApi::Api.new 'api key'
    value = api.query_by_email 'milboj@gmail.com'
    expect(value).to include('gender')
  end

  describe 'from configruation' do
    before :each do
      # Api key must has eam and email_validation
      TowerDataApi::Configuration.begin do |config|
        config.api_key = 'api_key'
      end
      @api = TowerDataApi::Api.new  
    end


    def check_email output
      expect(output.kind_of? Hash).to be true
      expect(output.keys).to include('eam')
      expect(output.keys).to include('email_validation')
      expect(output.keys).to include('gender')
    end

    it 'query by email' do
      simple_query1 = @api.query_by_email 'milboj@gmail.com'
      check_email simple_query1

      simple_query2 = @api.query_by_email 'milboj@gmail.com'
      check_email simple_query2
    end

    describe 'validation' do
      it 'valid email' do
        validation = @api.get_email_validation 'milboj@gmail.com'
        expect(validation.ok).to be true
        expect(validation.status_code).to be 50
        expect(validation.status).to eql 'valid'
        expect(validation.domain_type).to eql "freeisp"
        expect(validation.valid?).to be true
      end
      it 'invalid email' do
        validation = @api.get_email_validation 'milbojyu@yaho0.com'
        expect(validation.ok).to be false
        expect(validation.status_code).to be 315
        expect(validation.status).to eql 'invalid'
        # this requires email_correction filed
        expect(validation.email_correction).not_to be_empty
        expect(validation.domain_type).to be nil
        expect(validation.valid?).to be false
      end

      it 'raise error if validation is not available' do
        api =  TowerDataApi::Api.new 'simple_api_key' 
        expect{ api.get_email_validation 'milbojyu@yaho0.com'}.to raise_error(TowerDataApi::Error::Api)
      end

      it 'valid_email? method' do
        expect(@api.valid_email? 'milboj@gmail.com').to be true
        expect(@api.valid_email? 'milboj@com').to be false
      end

    end

    it 'query by md5' do
      md5_query = @api.query_by_md5 Digest::MD5.hexdigest('milboj@gmail.com')
      check_email md5_query
    end

    it 'query by sha1' do
      sha1_query = @api.query_by_sha1 Digest::SHA1.hexdigest('milboj@gmail.com')
      check_email sha1_query
    end

    it 'util_name_to_gender' do
      naz = @api.query_by_naz('bojan', 'milosavjevic', '111070')
      puts naz
    end

    it 'bulk query' do
      query = @api.bulk_query [{email: 'milboj@gmail.com'}, {email: 'milbojyu@yahoo.com'}]
      expect(query.class).to be Array
      expect(query).not_to be_empty
    end
  end
end
