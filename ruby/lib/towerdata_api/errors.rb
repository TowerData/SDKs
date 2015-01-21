module TowerDataApi
  module Error
    class Base < StandardError; end
    class Configuration < Base; end
    class Api < Base; end
    class BadRequest < Base; end
    class Unsupported < Base; end
  end
end
