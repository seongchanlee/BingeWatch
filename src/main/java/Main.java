import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String credential = args[0];

        JDABuilder builder = JDABuilder.createDefault(credential);
        builder.setActivity(Activity.playing("-bw help"));
        builder.addEventListeners(new MessageHandler());
        builder.build();
    }
}
