##
# Creates the class Interest as a model for the table and defines its relations to the other tables.
#
# Author: Karen Dana
# Version 27 November 2014
##
class Interest < Sequel::Model
    # Many users have likes, and each like has multiple users that like it
    many_to_many :user_likes, :class=>:User, :key=>:interest_id, :join_table=>:likes
    # Many users have dislikes, and each dislike has multiple users that dislike it
    many_to_many :user_dislikes, :class=>:User, :key=>:interest_id, :join_table=>:dislikes
end
