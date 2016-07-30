# Elm Sprockets Integration for Rails 3
#
# I'm working on turning this into a Gem - see https://github.com/NoRedInk/sprockets-elm - but
# I know a lot more about Elm than I do Rails, and haven't gotten that version working yet.
# If you know how to help Gemify this, by all means please hit me up! https://twitter.com/rtfeldman
# I could definitely use the help.
#
# Anyway, in the meantime, this is what we're using at NoRedInk to integrate Elm into our asset
# pipeline, and it works like a charm. Just copy this into config/initializers/elm_sprockets.rb
#
# For this to work, make sure you have elm-make on your PATH. You can get it with:
# 
# npm install -g elm 
#
# Now you should be able to //=require any *.js.elm file (must end with .js.elm and not just .elm!)
# from any JS file that Sprockets knows about, e.g.
#
# //= require MyElmModule.js.elm

class ElmCompiler
  class << self
    def call(input)
      instance.call(input)
    end

    def instance
      @instance ||= new
    end
  end

  def call(input)
    Tempfile.create(['temp', '.js']) do |f|
      `elm make #{input[:filename]} --output #{f.path}`

      f.rewind
      f.read
    end
  end
end

Sprockets.register_mime_type('text/x-elm', extensions: ['.js.elm'])
Sprockets.register_transformer('text/x-elm', 'application/javascript', ElmCompiler)
