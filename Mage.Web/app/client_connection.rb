class ClientConnection
  @connections = {}

  class << self
    def connect(username)
      # @connections.fetch(username) do
        @connections[username] = RubyClient.new(username)
      # end
    end
  end
end
