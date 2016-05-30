require 'angelo'
require 'tilt/haml'

require './client_connection'
require './lib/asset_routes'

class RubyMage < Angelo::Base
  include AssetRoutes

  get '/' do
    haml :'index.html'
  end

  post '/auth' do
    content_type :json
    { success: '123' }
  end

  websocket '/ws' do |ws|
    websockets << ws

    ClientConnection.connect('pheen')

    ws.on_message do |msg|
      ws.write 'hi :D'
    end
  end
end
