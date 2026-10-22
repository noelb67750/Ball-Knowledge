
package ballknowledge;

// javalin for the serverside logic
import io.javalin.Javalin;


import java.util.Map;

public class App {
    public static void main(String[] args)
        {
        // creates server object
        Javalin app = Javalin.create().start(7070);
        
        /*
        app - server obj
        get - method that handles get requests
        "/api/health" - path used to access end point 
        end point - a function that will be called when the path is accessed
        ctx - context object that contains info about requests and resposnes 
        ctx.json - method that sends a json response to the client
        map.of - method that creates a map object with key value pairs
        ("ok", true) - key value pair that will be sent in the json response
        */

        //
        app.get("/api/health", ctx -> ctx.json(Map.of("ok", true)));
    
        }
    }