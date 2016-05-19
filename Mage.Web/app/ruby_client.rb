require '../mage-web-client.jar'

import 'mage.web.Client'

class RubyClient
  def initialize
    @client = Java::MageWeb::Client.new('pheen', self)
    @client.connect()
    @session = @client.getSession()

    binding.pry
  end

  def connected(message) # String 
    puts 'connected'
    # binding.pry
  end

  def disconnected(error_call) # boolean 
    puts 'disconnected'
    # binding.pry
  end

  def showMessage(message) # String 
    puts 'showMessage'
    # binding.pry
  end

  def showError(message) # String 
    puts 'showError'
    # binding.pry
  end

  def processCallback(arg) # no idea. proc?
    puts 'processCallback'
    # binding.pry
  end
end

# RubyClient.new

require 'angelo'
require 'tilt/haml'

require './lib/asset_routes.rb'

class RubyMage < Angelo::Base
  include AssetRoutes

  get '/' do
    haml :'index.html'
  end

  post '/auth' do
    content_type :json
    { success: '123' }
  end

  websocket '/' do |ws|
    websockets << ws

    ws.on_message do |msg|
      ws.write msg
    end
  end
end
