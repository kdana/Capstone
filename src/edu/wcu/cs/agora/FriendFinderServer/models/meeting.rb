class Meeting < Sequel::Model
    many_to_many :attendees, :class=>:User, :key=>:meeting_id, :join_table=>:attendees
    many_to_many :invitees, :class=>:User, :key=>:meeting_id, :join_table=>:invitees
    many_to_one :owner, :class=>:User, :key=>:meeting_id
end
