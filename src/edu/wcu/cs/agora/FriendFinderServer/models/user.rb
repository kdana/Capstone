class User < Sequel::Model
    one_to_many :meetings
    many_to_many :invitees, :class=>:Meeting, :key=>:user_id, :join_table=>:invitees
    many_to_many :attendees, :class=>:Meeting, :key=>:user_id, :join_table=>:attendees
    one_to_many :circles
    many_to_one :circlees, :class=>:Circle, :key=>:user_id
    many_to_many :likes, :class=>:Interest, :key=>:user_id, :join_table=>:likes
    many_to_many :dislikes, :class=>:Interest, :key=>:user_id, :join_table=>:dislikes
end
