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
    @last_modified_known ||= File.mtime(FileName).to_i
    last_modified = File.mtime(FileName).to_i

    if @last_modified_known < last_modified
      warn 'Reloading RubyClient'

      load FileName
      @last_modified_known = last_modified
    end
  rescue Exception
    warn "autoreload failed unexpectedly: #{$!}"
  end
end
