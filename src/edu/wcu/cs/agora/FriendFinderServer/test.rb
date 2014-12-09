#!/usr/bin/ruby
require 'sinatra'
require_relative 'models'

#class HelloWorld < Sinatra::Base
    set :environment, :test
    set :port, 80
    get '/' do
        'Hello, World!'
    end

    get '/hi/?:name?' do
        "Hi #{params[:name] ? ' ' + params[:name] : 'there' }!"
    end

    get '/user/:username' do
        User.find(:name=>params[:username])
    end

    get '/create/user/:username/:email/:password/:age' do
        User.create(:name=>params[:username], :email=>params[:email], :password_hash=>params[:password], :password_salt=>"1234", :age=>params[:age])
    end
#end
