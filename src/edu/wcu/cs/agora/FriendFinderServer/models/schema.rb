require "rubygems"
require "sequel"

##
# Connects to the database and creates the tables if they don't exist
#
# Author: Karen Dana
# Version 27 November 2014
##

db = Sequel.connect(:adapter=>"mysql",  :user=>"ff_user",           :password=>"yWb5DoeEA5Rt",
                    :host=>"localhost", :database=>"friendfinder")

# Create the users table
db.create_table? :users do
    primary_key  :id
    String       :name,          :null => false
    String       :email,         :unique => true, :null => false
    String       :password_hash, :null => false
    String       :password_salt, :null => false
    File         :picture
    Integer      :age,           :null => false
    String       :gender
    String       :description
    Integer      :busy
    BigDecimal   :latitude
    BigDecimal   :longitude
    Date         :birthday
end

# Create meetings table
db.create_table? :meetings do
    primary_key  :id
    String       :name,       :null => false
    DateTime     :start_date, :null => false
    DateTime     :end_date
    BigDecimal   :latitude
    BigDecimal   :longitude
    foreign_key  :user_id, :users
end

# Create meeting_invitees table
db.create_table? :meeting_invitees do
    foreign_key  :user_id, :users
    foreign_key  :meeting_id, :meetings
end

#Create meeting_attendees table
db.create_table? :meeting_attendees do
    foreign_key  :user_id, :users
    foreign_key  :meeting_id, :meetings
end

# Create Circles table
db.create_table? :circles do
    primary_key  :id
    String       :name, :null => false
    String       :description
    foreign_key  :user_id, :users
end

# Create circlees table
db.create_table? :circlees do
    foreign_key  :user_id, :users
    foreign_key  :circle_id, :circles
end

# Create interests table
db.create_table? :interests do
    primary_key  :id
    String       :name, :null => false, :unique => true
end

#Create likes table
db.create_table? :likes do
    foreign_key  :user_id, :users
    foreign_key  :interest_id, :interests
end

#Create dislikes table
db.create_table? :dislikes do
    foreign_key  :user_id, :users
    foreign_key  :interest_id, :interests
end
