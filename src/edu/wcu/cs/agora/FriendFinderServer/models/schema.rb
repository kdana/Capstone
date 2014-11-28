require "rubygems"
require "sequel"

# Connect to database
#db = Sequel.connect(:adapter=>"mysql", :user=>"ff_user", :password=>"yWb5DoeEA5Rt",
#                     :host=>"kartemis.no-ip.org", :database=>"friendfinder")
db = Sequel.connect(:adapter=>"mysql", :user=>"root", :password=>"(*snowtiger*)",
                     :host=>"localhost", :database=>"friendfinder")

# Create the users table
db.create_table? :users do
    primary_key :id
    String :name, :null => false
    String :email, :unique => true, :null => false
    String :password_hash, :null => false
    String :password_salt, :null => false
    File :picture
    Integer :age, :null => false
    String :gender
    String :description
    Integer :busy
    Integer :latitude
    Integer :longitude
    Date :birthday
end

# Create meetings table
db.create_table? :meetings do
    primary_key :id
    String :name, :null => false
    DateTime :start_date, :null => false
    DateTime :end_date
    Integer :latitude
    Integer :longitude
    foreign_key :user_id, :users
end

# Create meeting_invitees table
db.create_table? :meeting_invitees do
    foreign_key :user_id, :users
    foreign_key :meeting_id, :meetings
end

#Create meeting_attendees table
db.create_table? :meeting_attendees do
    foreign_key :user_id, :users
    foreign_key :meeting_id, :meetings
end

# Create Circles table
db.create_table? :circles do
    primary_key :id
    String :name, :null => false
    String :description
    foreign_key :user_id, :users
end

# Create circlees table
db.create_table? :circlees do
    foreign_key :user_id, :users
    foreign_key :cirle_id, :circles
end

# Create interests table
db.create_table? :interests do
    primary_key :id
    String :name, :null => false, :unique => true
end

#Create likes table
db.create_table? :likes do
    foreign_key :user_id, :users
    foreign_key :interest_id, :interests
end

#Create dislikes table
db.create_table? :dislikes do
    foreign_key :user_id, :users
    foreign_key :interest_id, :interests
end