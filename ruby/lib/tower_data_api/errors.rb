module TowerDataApi
  module Error
    class Base < StandardError; end
    class Configuration < Base; end
    class Api < Base; end
  end
end
