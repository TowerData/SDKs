module TowerDataApi
  class EmailValidation
    attr_accessor :status_code, :status, :ok, :domain_type, :email_correction
    alias_method :valid?, :ok

    def initialize values
      values.each do |key, value|
        self.send("#{key}=", value)
      end
    end
  end
  
end
