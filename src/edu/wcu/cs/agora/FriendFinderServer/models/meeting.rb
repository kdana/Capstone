##
# Creates the class Meeting as a model for the table and defines its relations to the other tables.
#
# Author: Karen Dana
# Version 27 November 2014
##
class Meeting < Sequel::Model
    # Many users can attend meetings, and each user can attend many meetings
    many_to_many :attendees, :class=>:User, :key=>:meeting_id, :join_table=>:attendees
    # Many users can be invited to meetings, and each user can be invited to many meetings
    many_to_many :invitees, :class=>:User, :key=>:meeting_id, :join_table=>:invitees
    # Each meeting is created by a user
    many_to_one :owner, :class=>:User, :key=>:meeting_id
end
