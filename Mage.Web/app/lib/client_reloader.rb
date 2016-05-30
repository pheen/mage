class ClientReloader
  FileName = './ruby_client.rb'

  def self.run!
    Thread.new do
      reloader = new

      loop do
        reloader.run
        sleep(1)
      end
    end
  end

  def run
    @mtime      ||= File.mtime(FileName).to_i
    current_mtime = File.mtime(FileName).to_i

    if @mtime < current_mtime
      warn 'Reloading RubyClient'
      load FileName

      @mtime = current_mtime
    end
  rescue Exception
    warn "autoreload failed unexpectedly: #{$!}"
  end
end
