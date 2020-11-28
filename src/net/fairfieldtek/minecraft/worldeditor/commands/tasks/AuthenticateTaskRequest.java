package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.AuthenticateRequest;
import net.fairfieldtek.minecraft.worldeditor.http.AuthenticateResponse;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import org.bukkit.entity.Player;

public class AuthenticateTaskRequest
        extends HttpRequestor {

    private final String Uuid;

    public AuthenticateTaskRequest(Player player) {
        this.Uuid = player.getUniqueId().toString();
    }

    @Override
    public void run() {
//        try {

            Gson gson = new Gson();
            AuthenticateRequest authenticateRequest = new AuthenticateRequest();
            authenticateRequest.setUuid(this.Uuid);
            String body = gson.toJson(authenticateRequest);
            
            String hr =  RequestHttp(Initialization.BaseUri + "Authenticate", body);
            
            //System.out.println(hr);
            
            AuthenticateResponse response = gson.fromJson(
                   hr,
                    AuthenticateResponse.class);
            new AuthenticateTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
//        } catch (Exception e) {
//            RegisterResponse registerResponse = new RegisterResponse();
//            registerResponse.setMessage("An Error has occurred.");
//            registerResponse.setWasSuccessful(false);
//            registerResponse.setUuid(this.Uuid);
//            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
//        }
    }
}
