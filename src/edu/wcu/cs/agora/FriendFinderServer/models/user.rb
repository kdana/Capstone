##
# Creates the class User as a model for the table and defines its relations to the other tables.
#
# Author: Karen Dana
# Version 27 November 2014
##

class User < Sequel::Model
    # Each user creates meetings
    one_to_many :meetings
    # Each meeting can have multiple users invited, 
    # and each user can be invited to multiple meetings
    many_to_many :invitees, :class=>:Meeting, :key=>:user_id, :join_table=>:invitees
    # Each meeting can have multiple users attending, 
    # and each user can attend multiple meetings
    many_to_many :attendees, :class=>:Meeting, :key=>:user_id, :join_table=>:attendees
    # Each user defines their own circles
    one_to_many :circles
    # Each of those circles contain multiple users, and each user can be in multiple circles
    many_to_many :circlees, :class=>:Circle, :key=>:user_id, :join_table=>:ciclees
    # Each user can have multiple interests they like 
    # and each interest can have multiple people like it
    many_to_many :likes, :class=>:Interest, :key=>:user_id, :join_table=>:likes
    # Each user can have multiple interests they dislike 
    # and each interest can have multiple people dislike it
    many_to_many :dislikes, :class=>:Interest, :key=>:user_id, :join_table=>:dislikes
end
