require 'java'
require 'pry'
require './ruby_client'
require './ruby_mage'
require './lib/client_reloader'

ClientReloader.run!
RubyMage.run!
