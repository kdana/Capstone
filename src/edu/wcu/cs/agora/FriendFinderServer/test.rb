#!/usr/bin/ruby
require 'sinatra'

#class HelloWorld < Sinatra::Base
    set :environment, :test
    set :port, 80
    get '/' do
        'Hello, World!'
    end

    get '/hi/?:name?' do
        "Hi #{params[:name] ? ' ' + params[:name] : 'there' }!"
    end
#end
