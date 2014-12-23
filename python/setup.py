from distutils.core import setup

setup(
    name='TowerDataApi',
    author='TowerData',
    author_email='developer@towerdata.com',
    version='0.2.0',
    packages=['towerDataApi'],
    url='http://www.towerdata.com',
    description='A library for interacting with TowerData\'s Personalization API',
    keywords='towerdata api',
    long_description=open('README.txt').read(),
    requires=['urllib3'],
)
