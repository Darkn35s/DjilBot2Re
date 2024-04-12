package dji.lbot2Re.Interfaces;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;
public interface ICommand {
    void handle(List<String> args, MessageReceivedEvent event);
    String gethelp();
    String getInvoke();

}
