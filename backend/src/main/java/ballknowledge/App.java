/*
noel baca

this app can directly use backed features when in the
same package.
*/
package ballknowledge;

// javalin for the serverside logic
import io.javalin.Javalin;

//import map in order to return a json object
import java.util.Map;

/*
this is the class that will run the server side
*/
public class App {
    public static void main(String[] args)
        {
        
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.allowHost("http://localhost:5173", "http://localhost:3000");
                });
            });
        }).start(8080);
        app.get("/api/health", ctx -> ctx.json(Map.of("ok", true)));
        }

    }