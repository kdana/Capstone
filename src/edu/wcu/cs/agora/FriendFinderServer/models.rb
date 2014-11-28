#!/bin/ruby

require_relative 'models/schema'
require_relative 'models/user'
require_relative 'models/meeting'
require_relative 'models/interest'
require_relative 'models/circle'

bob = User.first
bill = User.last
puts bob.inspect
puts bill.inspect
