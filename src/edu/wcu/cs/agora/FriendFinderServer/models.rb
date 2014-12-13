#!/usr/bin/ruby

##
# Brings together tables and models for the database
#
# Author: Karen Dana
# Version: 27 November 2014
##


require_relative 'models/schema'
require_relative 'models/user'
require_relative 'models/meeting'
require_relative 'models/interest'
require_relative 'models/circle'

# Tests to ensure database is working
#puts User.first.inspect
#puts User.last.inspect
