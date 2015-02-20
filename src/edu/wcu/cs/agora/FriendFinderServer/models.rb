require 'sequel'
require 'json'

#!/usr/bin/ruby

##
# Brings together tables and models for the database
#
# Author: Karen Dana
# Version: 27 November 2014
##

# Gives all subclasses JSON output capabilities
Sequel::Model.plugin :json_serializer

require_relative 'models/schema'
require_relative 'models/user'
require_relative 'models/meeting'
require_relative 'models/interest'
require_relative 'models/circle'

#puts User.first.inspect
#puts User.last.inspect
