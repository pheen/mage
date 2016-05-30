(function() {
  var ws;

  ws = new WebSocket("ws://" + location.host + "/ws");

  ws.onmessage = function(e) {
    return console.log(e.data);
  };

  ws.onopen = function(e) {
    return ws.send('hi');
  };

}).call(this);
