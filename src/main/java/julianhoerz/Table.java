package julianhoerz;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


class Table implements HttpHandler {

    private Graph graph;


    Table(Graph graph){
        this.graph = graph;

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
            httpExchange.sendResponseHeaders(204, -1);
            return;
        }

        String response = createResponse();
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private String createResponse(){
        String name = "bremen";
        Integer NodesLength = graph.getNodesLength();
        Integer EdgesLength = graph.getEdgesLength();

        String responseJson = "{\"Name\":\"" + name + "\", \"Nodes\":\"" + NodesLength.toString() + "\", \"Edges\":\"" + EdgesLength.toString() + "\"}";

        return responseJson;
    }
}