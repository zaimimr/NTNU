"use strict";

const net = require("net");
const crypto = require("crypto");

const generateAcceptValue = acceptKey =>
  crypto
    .createHash("sha1")
    .update(acceptKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11", "binary")
    .digest("base64");

// Simple HTTP server responds with a simple WebSocket client test
const httpServer = net.createServer(connection => {
  connection.on("data", () => {
    let content = `<!DOCTYPE html>
    <html>
      <head>
        <meta charset="UTF-8" />
      </head>
      <body>
        WebSocket test page
        <script>
          let ws = new WebSocket('ws://localhost:3001');

          ws.onmessage = event => document.getElementById("nope").innerHTML = document.getElementById("nope").innerHTML + "</br> Friend: "+ event.data;

          const foo = () => {
            let v = document.getElementById("message").value
            ws.send(v);
            document.getElementById("nope").innerHTML = document.getElementById("nope").innerHTML + "</br> Me: "+ v;
          }
        </script>
        <br/>
        <label for="lname">Skriv melding:</label>
        <input type="text" id="message" name="message"><br><br>
        <input type="submit" value="Submit" onclick="return foo();">
        <div id="nope" class="div">
        </div>
        <style>
          .div {
            border: 1px solid black;
            width: 500px;
            height: auto;
            margin-top: 10px;
          }
        </style>
      </body>
    </html>
    `;
    connection.write(
      "HTTP/1.1 200 OK\r\nContent-Length: " +
        content.length +
        "\r\n\r\n" +
        content
    );
  });
});
httpServer.listen(3000, () => {
  console.log("HTTP server listening on port 3000");
});

let clients = [];
// Incomplete WebSocket server
const wsServer = net.createServer(connection => {
  console.log("Client connected");

  connection.on("data", data => {
    if (data.toString()[0] == "G") {
      var key = data
        .toString()
        .substring(
          data.toString().indexOf("-Key: ") + 6,
          data.toString().indexOf("==") + 2
        );
      var acceptValue = generateAcceptValue(key);

      const responseHeaders = [
        "HTTP/1.1 101 Web Socket Protocol Handshake",
        "Upgrade: websocket",
        "Connection: Upgrade",
        "Sec-WebSocket-Accept:" + acceptValue
      ];
      connection.write(responseHeaders.join("\r\n") + "\r\n\r\n");
      clients.push(connection);
    } else {
      let message = "";
      let length = data[1] & 127;
      let maskStart = 2;
      let dataStart = maskStart + 4;
      for (let i = dataStart; i < dataStart + length; i++) {
        let byte = data[i] ^ data[maskStart + ((i - dataStart) % 4)];
        message += String.fromCharCode(byte);
      }
      sendMessageToClients(message, connection);
    }
  });

  connection.on("end", () => {
    console.log("Client disconnected");
  });
});

const sendMessageToClients = (message, c) => {
  let buffer = Buffer.concat([
    new Buffer([
      0x81,
      "0x" +
        (message.length + 0x10000)
          .toString(16)
          .substr(-2)
          .toUpperCase()
    ]),
    Buffer.from(message)
  ]);
  console.log(buffer);
  clients.forEach(client => {
    if (c != client) client.write(buffer);
  });
};

wsServer.on("error", error => {
  console.error("Error: ", error);
});
wsServer.listen(3001, () => {
  console.log("WebSocket server listening on port 3001");
});
