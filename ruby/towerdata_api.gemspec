# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = %q{towerdata_api}
  s.version = "2.0.0"

  s.required_rubygems_version = Gem::Requirement.new(">= 1.2") if s.respond_to? :required_rubygems_version=
  s.authors = ["TowerData"]
  s.date = %q{2014-12-17}
  s.description = %q{A library for interacting with TowerData's Personalization and Utilities APIs.}
  s.email = %q{developer @nospam@ towerdata.com}
  s.extra_rdoc_files = ["CHANGELOG", "LICENSE", "README.md", "lib/towerdata_api.rb"]
  s.files = Dir["{lib}/**/*"] + ["CHANGELOG", "LICENSE", "Manifest", "README.md", "Rakefile", "towerdata_api.gemspec"]
  s.homepage = %q{http://www.towerdata.com}
  s.rdoc_options = ["--line-numbers", "--inline-source", "--title", "Rapleaf_api", "--main", "README.md"]
  s.require_paths = ["lib"]
  s.rubyforge_project = %q{towerdata_api}
  s.license = 'Apache-2.0'
  
  s.rubygems_version = ">=1.6.1"
  s.summary = %q{A library for interacting with TowerData's Personalization API.}
  s.add_development_dependency "rspec", '~>3.1'
  s.add_development_dependency "simplecov", '~>0.16.0'

end
