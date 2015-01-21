from distutils.core import setup

setup(
    name='TowerDataApi',
    author='TowerData',
    author_email='developer@towerdata.com',
    version='0.2.1',
    packages=['towerDataApi'],
    url='http://www.towerdata.com',
    description='A library for interacting with TowerData\'s Personalization API',
    keywords='towerdata api',
    requires=['urllib3'],
)
