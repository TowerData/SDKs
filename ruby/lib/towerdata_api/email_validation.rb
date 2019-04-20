module TowerDataApi
  class EmailValidation
    attr_accessor :status_code, :status, :domain_type, :email_corrections, :address

    def initialize values
      values.each do |key, value|
        self.send("#{key}=", value)
      end
    end

    def ok
      status == "valid"
    end

    def valid?
      ok
    end
  end
  
end
