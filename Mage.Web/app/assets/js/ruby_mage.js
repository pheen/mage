ws = new WebSocket("ws://#{location.host}/ws")

ws.onmessage = (e) ->
  console.log e.data

ws.onopen = (e) ->
  ws.send 'hi'
