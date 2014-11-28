class Interest < Sequel::Model
    many_to_many :user_likes, :class=>:User, :key=>:interest_id, :join_table=>:likes
    many_to_many :user_dislikes, :class=>:User, :key=>:interest_id, :join_table=>:dislikes
end
