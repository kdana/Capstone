class Circle < Sequel::Model
    many_to_many :circlees, :class=>:User, :key=>:circle_id, :join_table => :circlees
    many_to_one :owner, :class=>:User, :key=>:circle_id
end
