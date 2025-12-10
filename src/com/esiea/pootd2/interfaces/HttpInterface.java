package com.esiea.pootd2.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.esiea.pootd2.controllers.IExplorerController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Implementation of a Web Interface using Java's built-in HttpServer.
 * It allows the user to control the file explorer via a browser (localhost:8001).
 */
public class HttpInterface extends AbstractInterface implements HttpHandler {
    private HttpServer server;
    private ExecutorService threadPool;
    private boolean serverShouldClose;

    /**
     * Initializes the HTTP server on port 8001.
     */
    public HttpInterface(IExplorerController controller) {
        super(controller);
        serverShouldClose = false;
        
        try {
            // Create server listening on all network interfaces (0.0.0.0) port 8001
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 0);
            server.createContext("/", this);
            
            // Use a single thread to handle requests sequentially
            threadPool = Executors.newFixedThreadPool(1);
            server.setExecutor(threadPool);
        } catch (IOException e) {
            System.err.println("Failed to initialize HttpInterface");
            e.printStackTrace();
            server = null;
        }
    }

    /**
     * Starts the server and blocks the main thread until the user types 'exit'.
     */
    public void run() {
        assert server != null;
        server.start();
        System.out.println("Server listening on http://localhost:8001");
        
        try {
            // Loop until the 'exit' command sets serverShouldClose to true
            while (!serverShouldClose)
                Thread.sleep(200);
            System.out.println("Server closed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Cleanup resources
        server.stop(1);
        threadPool.shutdownNow();
    }

    /**
     * Main handler for incoming HTTP requests.
     * Dispatches requests based on the HTTP method (GET or POST).
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. POST request on /execute -> Run a command
        if ("POST".equals(exchange.getRequestMethod())) {
            URI uri = exchange.getRequestURI();
            if ("/execute".equals(uri.getPath())) {
                handlePostExecute(exchange);
                return;
            }
        }

        // 2. GET request -> Serve the HTML page
        if ("GET".equals(exchange.getRequestMethod())) {
            handleGetDefault(exchange);
            return;
        }

        handleError(exchange);
    }

    /**
     * Serves the HTML frontend page to the browser.
     */
    private void handleGetDefault(HttpExchange exchange) throws IOException {
        OutputStream stream = exchange.getResponseBody();
        String htmlResponse = constructWebPage();
        
        exchange.sendResponseHeaders(200, htmlResponse.length());
        stream.write(htmlResponse.getBytes());
        stream.flush();
        stream.close();
    }

    /**
     * Reads the command from the request body, executes it via the Controller,
     * and sends the result back to the browser.
     */
    private void handlePostExecute(HttpExchange exchange) throws IOException {
        assert controller != null;
        
        InputStream inStream = exchange.getRequestBody();
        OutputStream outStream = exchange.getResponseBody();

        // Read the command string sent by the JavaScript frontend
        String command = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);

        String response = "";
        if ("exit".equals(command)) {
            System.out.println("Closing server...");
            response = "Server closed";
            serverShouldClose = true; // Signal the run() loop to stop
        }
        else
            // Delegate execution to the controller
            response = controller.executeCommand(command);

        // Send response back
        exchange.sendResponseHeaders(200, response.length());
        outStream.write(response.getBytes());
        outStream.flush();
        outStream.close();
    }

    private void handleError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(400, 0);     
        exchange.close();   
    }

    /**
     * Returns the raw HTML/CSS/JS string that makes up the user interface.
     */
    private String constructWebPage() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>File explorer</title>
                <style>
                    * { font-family: 'Courier New', Courier, monospace; }
                    html, body { width: 100%; height: 100%; padding: 0; margin: 0; }
                    .console { width: calc(100vw - 10px); min-height: calc(100vh - 10px); background: #1c1c1c; color: white; padding: 5px; font-size: 16px; font-weight: bold; }
                    #console-user-input { background: none; outline: none; border: none; color: inherit; font-size: inherit; font-weight: inherit; margin: none; padding: none; width: calc(100% - 25px); }
                </style>
            </head>
            <body>
                <div class="console">
                    <div id="console-content"></div>
                    <div id="console-input">> <input type="text" id="console-user-input" /></div>
                </div>
            </body>
            <script text="javascript">
                var consoleContent = document.getElementById("console-content");
                var consoleInput = document.getElementById("console-input");
                var userInput = document.getElementById("console-user-input");
                
                function request(method, url, headers, data) {
                    return new Promise((resolve, reject) => {
                        let xhttp = new XMLHttpRequest();
                        xhttp.onreadystatechange = () => {
                            if (xhttp.readyState == 4) {
                                if (xhttp.status >= 200 && xhttp.status < 300) resolve({ status: xhttp.status, body: xhttp.responseText });
                                else reject({ status: xhttp.status, body: xhttp.responseText });
                            }
                        };
                        xhttp.onabort = xhttp.onerror = () => { reject({ status: 0, body: null }); };
                        xhttp.open(method, url, true);
                        for (let key of Object.keys(headers)) xhttp.setRequestHeader(key, headers[key]);
                        xhttp.send(data);
                    });
                }
                
                document.addEventListener("keydown", (e) => {
                    if (e.key != "Enter") return;
                    let command = userInput.value;
                    consoleContent.innerText += `> ${userInput.value}\\n`;
                    consoleInput.style.display = "none";
                    userInput.value = "";
                    request("POST", `http://${window.location.hostname}:8001/execute`, {}, command).then(res => {
                        if (res.body.length > 0) consoleContent.innerText += `${res.body}\\n`
                        consoleInput.style.display = "block";
                        userInput.focus();
                    }).catch(err => {
                        consoleInput.style.display = "block";
                        userInput.focus();
                    });
                })
            </script>
            </html>
                """;
    }
}