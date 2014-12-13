##
# Creates the class Circle as a model for the table and defines its relations to the other tables.
#
# Author: Karen Dana
# Version 27 November 2014
##

class Circle < Sequel::Model
    # Many members can be in a circle, and each member can be in many circles
    many_to_many :users, :class=>:User, :key=>:circle_id, :join_table => :circlees
    # Each circle is created by a user
    many_to_one :owner, :class=>:User, :key=>:circle_id
end
