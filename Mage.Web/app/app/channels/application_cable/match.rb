# Be sure to restart your server when you modify this file. Action Cable runs in a loop that does not support auto reloading.
module ApplicationCable
  class MatchChannel < ActionCable::Channel::Base
    def subscribed
      stream_from 'match_channel'
    end
  end
end
