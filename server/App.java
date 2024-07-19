package server_test;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App {
    public static void main(String[] args) {
        try {
            int port = 5678;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            // 클라이언트가 port의 /, content-type.json 요청을 보낸다면, 핸들 실행.
            server.createContext("/", new GreetingHandler());
            server.createContext("/sum", new SumHandler());
            server.setExecutor(null);
            server.start();

            System.out.println("port: " + port);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static class GreetingHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 응답할 메시지
            String response = """
                    {
                        "message" : "server check"
                    }
                    """;
            int responseLength = response.getBytes().length;

            // 리스폰스 헤더에 json type으로 추가
            Headers headers = exchange.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Content-Length", String.valueOf(responseLength));

            // 보낼 응답 헤더에 200 코드와, 응답의 길이를 담아서 보냄.
            exchange.sendResponseHeaders(200, responseLength);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SumHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // read file
            String dataPath = "C:\\Users\\user\\Desktop\\server_test\\app\\src\\main\\resources\\user.json";

            Reader reader = new FileReader(dataPath);

            // 저장할 결과
            int sum = 0;

            // json parsing.
            JSONParser jsonParser = new JSONParser();

            JSONArray jsonArray;
            try {
                jsonArray = (JSONArray)jsonParser.parse(reader);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj = (JSONObject)jsonArray.get(i);
                    int postCount = Integer.valueOf(jsonObj.get("post_count").toString());
                    sum += postCount;
                }
            } catch (IOException | ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String response = "{ \"sum\": \"" + sum + "\" }";
            int responseLength = response.getBytes().length;

            // 리스폰스 헤더에 json type으로 추가
            Headers headers = exchange.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Content-Length", String.valueOf(responseLength));

            // 보낼 응답 헤더에 200 코드와, 응답의 길이를 담아서 보냄.
            exchange.sendResponseHeaders(200, responseLength);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(response);
        }

    }
}
