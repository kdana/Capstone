#!/usr/bin/ruby
require 'sinatra'
require 'json'
require_relative 'models'

class Server < Sinatra::Base
    #set :bind, '0.0.0.0'
    set :environment, :production
    #set :environment, :test
    set :port, 443

    get '/' do
        "Hello World!"
    end


## Returns Information about an Instance of a Class ##


    # When page /user/ is accessed, finds a user with the id given after the slash from the 
    # database and returns it as text
    get '/user/:id' do |id|                     # a ':' before the name means it is a parameter 
        User[id].to_json
    end

    # Retrieves a user's likes
    get '/user/likes/:id' do |id|
        user = User[id];
        user.add_like("basketball")
        user.interests.collect{|like| like.name}.join(", ")
    end

    # Retrieves a user's dislikes
    get '/user/dislikes/:id' do |id|
        user = User[id];
        user.dislikes.collect{|dislike| dislike.name}.join(", ")
    end

    # When page /user/email/ is accessed, finds a user with the email given after the slash from
    # the database and returns it as text
    get '/user/email/:email' do |email|
        User[:email=>email].to_json
    end

    # When page /meeting/ is accessed, finds a meeting with the id given after the slash from the 
    # database and returns it as text
    get '/meeting/:id' do |id|
        Meeting[id]
    end

    # Retrieves a circle's information given the id number
    get '/circle/:circle_id' do |id|
        Circle[id]
    end


## Retrieves Lists Specific to User ##


    # Retrieves the members of a user's circle
    get '/circlees/:circle_id' do |id|
        circle = Circle[id]
        circle.users.collect{|user| user.values}.join('<br>')
    end

    # Retrieves the names of the members in a user's circle
    get '/circlees/owner/:user_id' do |id|
        user = User[id]
        user.circles.collect{|circle| circle.users.collect{|user| user.name}.join(', ')}.join('<br>')
        #Circle.where(:owners=>user).all
    end

    # Retrieves list of events user is attending
    get '/user/events/:user_id' do |id|
        user = User[id]
        user.meetings.collect{|meeting| meeting.values}.join('<br>')
    end


## Creates Classes with Information Given ##


    # Creates a user with the given required information
    get '/create/user/:name/:email/:password/:age' do |name, email, password, age|
        User.create(:name=>name, :email=>email, :password_hash=>password, 
                    :password_salt=>"1234", :age=>age)
    end

    # Creates a meeting with the given required information
    get '/create/meeting/:meeting/:start/:user_id' do |meeting, start, user_id|
        # DateTime format = YYYY-MM-DDTHH:MM:SS+/-HH:MM
        # Year, month, day, T (time), hour, minute, second, + or - timezone hours and minutes
        Meeting.create(:name=>params[:meeting], :start_date=>params[:start], :user_id=>user_id)
    end

    # Creates a circle for the given user with description as an optional parameter
    get '/create/circle/:user_id/:name/?:description?' do |user_id, name, description|
        Circle.create(:user_id=>user_id, :name=>name, :description=>description)
    end


## Makes Changes to a Class's Attributes ##


    # Changes the given attribute of the given user to value of the item passed in
    get '/user/change/:item_type/:user_id/:new_item' do |item_type, user_id, new_item|
        User[user_id].update(:"#{item_type}"=>new_item)
        true
    end


## Adds to a Join Table ##


    # Adds a user to another user's circle
    get '/circle/add/:circle_id/:user_id' do |circle_id, user_id|
        circle = Circle[circle_id]
        user = User[user_id]
        circle.add_user(user)
    end

    # Adds a meeting attendee
    get '/meeting/add/:meeting_id/:user_id' do |meeting_id, user_id|
        meeting = Meeting[meeting_id]
        user = User[user_id]
        meeting.add_meeting_attendee(user)
        #user.add_meeting(meeting)
    end
end
