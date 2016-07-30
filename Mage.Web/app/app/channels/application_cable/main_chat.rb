# Be sure to restart your server when you modify this file. Action Cable runs in a loop that does not support auto reloading.
module ApplicationCable
  class MainChatChannel < ActionCable::Channel::Base
    def subscribed
      stream_from 'main_chat_channel'
    end
  end
end
