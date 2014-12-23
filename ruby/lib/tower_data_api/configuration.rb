module TowerDataApi
  class Configuration

    @@api_key  = nil
    @@ca_file = nil
    @@bulk_timeout = 30
    @@timeout = 2
    
    def self.api_key= api_key
      @@api_key = api_key
    end

    def self.api_key
      @@api_key 
    end

    def self.ca_file= ca_file
      @@ca_file = ca_file
    end

    def self.ca_file
      @@ca_file 
    end
    
    def self.timeout= timeout
      @@timeout = timeout
    end

    def self.timeout
      @@timeout 
    end

    def self.bulk_timeout= timeout
      @@bulk_timeout = timeout
    end

    def self.timeout
      @@timeout 
    end

    def self.begin
      yield self
    end

  end
end
