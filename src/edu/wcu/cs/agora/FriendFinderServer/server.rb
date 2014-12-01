#!/usr/bin/ruby
require 'sinatra'

class Server < Sinatra::Base
    #set :bind, '0.0.0.0'
    #set :environment, :production
    set :environment, :test
    set :port, 80

    get '/' do
        puts "Hello World!"
    end

  # ...

  def self.run!
    rack_handler_config = {}
    ssl_options = {
      :private_key_file => '~/ssl/server.key',
      :cert_chain_file => '~/ssl/server.crt',
      :verify_peer => false,
    }

    Rack::Handler::Thin.run(self, rack_handler_config) do |server|
      server.ssl = true
      server.ssl_options = ssl_options
    end
  end
end

Server.run!

#Server.run! do |server|
#  ssl_options = {
#    :cert_chain_file => '~/ssl/server.crt',
#    :private_key_file => '~/ssl/server.key',
#    :verify_peer => false
#  }
#  server.ssl = true
#  server.ssl_options = ssl_options
#end
