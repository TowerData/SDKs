module TowerDataApi
  class Configuration

    @@api_key  = nil
    @@ca_file = nil
    @@bulk_timeout = 30
    @@val_timeout = 11
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

    def self.bulk_timeout
      @@bulk_timeout
    end

    def self.val_timeout= timeout
      @@val_timeout = timeout
    end

    def self.val_timeout
      @@val_timeout
    end

    def self.begin
      yield self
    end

  end
end
