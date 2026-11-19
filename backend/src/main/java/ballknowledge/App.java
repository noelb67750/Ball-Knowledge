
package ballknowledge;

// javalin for the serverside logic
import io.javalin.Javalin;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * runs a javalin server on a port. Calls for data. CORS is endabled so the frontend origin can read the servers response 
 */
public class App {
    public static final int PORT = 7070;
    public static final String FRONTEND_ORIGIN = "http://localhost:5173";
    public static final int ROUNDS = 10;
    // concurent hashmap to store multiple drafts
    private static final Map<String, Draft> drafts = new ConcurrentHashMap<>();
    public static void main(String[] args)
        {
        // Create the server and allow the React frontend to read its responses (CORS)
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
            cors.addRule(it -> it.allowHost(FRONTEND_ORIGIN));
            });
        }).start(PORT);
        
    
        app.get("/api/health", ctx -> ctx.json(Map.of("ok", true)));
        app.post("/api/drafts", ctx -> {
            //checks if req is valid
            DraftRequest req;
            try {
                req = ctx.bodyAsClass(DraftRequest.class);
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "Invalid request body"));
                return;
            }
            String uniqueCode = java.util.UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            // checks if draft is valid
            // and saves it to the drafts map if it is valid
            try {
                Draft draft = new Draft(req.teams(), ROUNDS, req.budget(), uniqueCode);
                drafts.put(uniqueCode, draft);
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error",e.getMessage()));
                return;
            }
            ctx.status(201).json(Map.of("draftCode", uniqueCode));
       });
    }
}