module AssetRoutes
  def self.included(base)
    base.get '/assets/(?<path>.*)', type: :regexp do
      responder.headers['Content-Type'] = content_type
      File.read("public/#{params[:path]}")
    end
  end

  def content_type
    case params[:path]
      when /\.js$/
        'application/javascript'
      when /\.css$/
        'text/css'
    end
  end
end
