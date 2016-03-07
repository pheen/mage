require 'java'
require 'pry'
require 'mage-web-client.jar'

import 'mage.web.Client'


class RubyClient
  def initialize
    client = Java::MageWeb::Client.new('pheen', self)
    client.connect()
  end

  def connected(message) # String 
    puts 'connected'
    binding.pry
  end

  def disconnected(error_call) # boolean 
    puts 'disconnected'
    binding.pry
  end

  def showMessage(message) # String 
    puts 'showMessage'
    binding.pry
  end

  def showError(message) # String 
    puts 'showError'
    binding.pry
  end

  def processCallback(arg) # no idea. proc?
    puts 'processCallback'
    binding.pry
  end
end

RubyClient.new
