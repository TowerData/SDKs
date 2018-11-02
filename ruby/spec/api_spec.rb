require 'spec_helper'
describe 'api_calls' do
  API_KEY= ''
  SIMPLE_API_KEY= ''
  it 'query without configuration' do
    api = TowerDataApi::Api.new API_KEY
    value = api.query_by_email 'demo@towerdata.com'
    expect(value).to include('gender')
    expect(value).to include('postal_address')
  end

  describe 'from configruation' do
    before :each do
      # Api key must has eam and email_validation
      TowerDataApi::Configuration.begin do |config|
        config.api_key = API_KEY
      end
      @api = TowerDataApi::Api.new
    end


    def check_email output
      expect(output.kind_of? Hash).to be true
      expect(output.keys).to include('eam')
      expect(output.keys).to include('gender')
    end

    it 'query by email' do
      simple_query1 = @api.query_by_email 'demo@towerdata.com'
      check_email simple_query1

      simple_query2 = @api.query_by_email 'demo@towerdata.com'
      check_email simple_query2
    end

    it 'query by email with fields' do
      simple_query1 = @api.query_by_email 'demo@towerdata.com', fields: 'gender'
      expect(simple_query1.keys).to include('gender')
      expect(simple_query1.keys.length).to eql(1)

      simple_query2 = @api.query_by_email 'demo@towerdata.com', fields: 'zip,gender'
      expect(simple_query2.keys).to include('gender')
      expect(simple_query2.keys).to include('zip')
      expect(simple_query2.keys.length).to eql(2)
    end

    describe 'validation' do

      it 'through validate_email' do
        validation = @api.validate_email 'demo@towerdata.com'
        expect(validation).to include('email_validation')
      end

      it 'through validate_email with timeout' do
        validation = @api.validate_email 'demo@towerdata.com', 1
        expect(validation).to include('email_validation')
      end

      it 'valid email' do
        validation = @api.email_validation 'demo@towerdata.com'
        expect(validation.ok).to be true
        expect(validation.status_code).to be 50
        expect(validation.status).to eql 'valid'
        expect(validation.domain_type).to eql "biz"
        expect(validation.valid?).to be true
      end
      it 'invalid email' do
        validation = @api.email_validation 'milbojyu@yaho0.com'
        expect(validation.ok).to be false
        expect(validation.status_code).to be 325
        expect(validation.status).to eql 'invalid'
        # this requires email_correction filed
        expect(validation.email_corrections).not_to be_empty
        expect(validation.domain_type).to be nil
        expect(validation.valid?).to be false
      end

      it 'raise error if validation is not available' do
        api =  TowerDataApi::Api.new SIMPLE_API_KEY
        expect{ api.email_validation 'demo@towerdata.com'}.to raise_error(TowerDataApi::Error::Unsupported)
      end

      it 'valid_email? method' do
        expect(@api.valid_email? 'demo').to be false
        expect(@api.valid_email? '').to be false
        expect(@api.valid_email? 'demo@towerdata.com').to be true
        expect(@api.valid_email? 'demo@com').to be false
      end

    end

    it 'query by md5' do
      md5_query = @api.query_by_md5 Digest::MD5.hexdigest('demo@towerdata.com')
      check_email md5_query
    end

    it 'query by sha1' do
      sha1_query = @api.query_by_sha1 Digest::SHA1.hexdigest('demo@towerdata.com')
      check_email sha1_query
    end

    it 'query by naz' do
      naz = @api.query_by_naz('bojan', 'milosavjevic', '111070')
      expect( naz.class).to be Hash
      expect( naz["gender"]).to eql "Male"
    end

    it 'query by nap' do
      nap = @api.query_by_nap('john', 'doe', 'Street 1', 'New York', 'NY')
      expect( nap.class).to be Hash
      expect( nap["gender"]).to eql "Male"
    end

    it 'query by nap with email option' do
      nap = @api.query_by_nap('john', 'doe', 'Street 1', 'New York', 'NY', email: 'demo@towerdata.com')
      expect( nap.class).to be Hash
      expect( nap["gender"]).to eql "Male"
    end

    it 'query by nap with fields option' do
      nap = @api.query_by_nap('john', 'doe', 'Street 1', 'New York', 'NY', fields: 'zip')
      expect(nap.class).to be Hash
      expect(nap.keys.empty?).to eq(true)
    end

    it 'bulk query' do
      query = @api.bulk_query [{email: 'sample@towerdata.com'}, {email: 'demo@towerdata.com'}]
      expect(query.class).to be Array
      expect(query).not_to be_empty
    end

    it 'bulk query with fields' do
      query = @api.bulk_query [{email: 'sample@towerdata.com'}, {email: 'demo@towerdata.com'}], 'gender,zip'
      expect(query.class).to be Array
      expect(query).not_to be_empty
      expect(query[0].keys.length).to eql(2)
    end

    it 'append email' do
      append = @api.append_email('john', 'doe', 'Street 1', 'New York', 'NY', 11100)
      expect(append.class).to be Hash
    end

    it 'append postal' do
      append = @api.append_postal('demo@towerdata.com')
      expect(append.class).to be Hash
      expect(append['postal_address'].keys.empty?).to eql(false)
    end
  end
end
