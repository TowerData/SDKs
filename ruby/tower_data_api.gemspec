# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = %q{tower_data_api}
  s.version = "1.3"

  s.required_rubygems_version = Gem::Requirement.new(">= 1.2") if s.respond_to? :required_rubygems_version=
  s.authors = ["TowerData"]
  s.date = %q{2014-12-17}
  s.description = %q{A library for interacting with TowerData's Personalization and Utilities APIs.}
  s.email = %q{developer @nospam@ towerdata.com}
  s.extra_rdoc_files = ["CHANGELOG", "LICENSE", "README.md", "lib/tower_data_api.rb"]
  s.files = ["CHANGELOG", "LICENSE", "Manifest", "README.md", "Rakefile", "lib/tower_data_api.rb", "tower_data_api.gemspec"]
  s.homepage = %q{http://www.towerdata.com}
  s.rdoc_options = ["--line-numbers", "--inline-source", "--title", "Rapleaf_api", "--main", "README.md"]
  s.require_paths = ["lib"]
  s.rubyforge_project = %q{tower_data_api}
  s.rubygems_version = ">=1.6.1"
  s.summary = %q{A library for interacting with TowerData's Personalization API.}
  s.add_development_dependency "rspec"
  s.add_dependency(%q<json>, [">= 0"])

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<json>, [">= 0"])
    else
      s.add_dependency(%q<json>, [">= 0"])
    end
  else
    s.add_dependency(%q<json>, [">= 0"])
  end
end
