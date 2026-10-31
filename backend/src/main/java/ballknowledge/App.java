
package ballknowledge;

// javalin for the serverside logic
import io.javalin.Javalin;


import java.util.Map;

/**
 * 
 * runs a javalin server on a port. Calls for data. CORS is endabled so the frontend origin can read the servers response 
 */
public class App {
    public static final int PORT = 7070;
    public static final String FRONTEND_ORIGIN = "http://localhost:5173";
    public static void main(String[] args)
        {
        // Create the server and allow the React frontend to read its responses (CORS)
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
            cors.addRule(it -> it.allowHost(FRONTEND_ORIGIN));
            });
        }).start(PORT);
        
    
        app.get("/api/health", ctx -> ctx.json(Map.of("ok", true)));
    
        }
    }