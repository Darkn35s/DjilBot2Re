package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class VersionCommand implements ICommand {
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        StringBuilder message = new StringBuilder();
        try(FileReader reader = new FileReader("version.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                message.append(c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        event.getMessage().replyEmbeds(BotUtils.CommonMessage(message.toString() ,event.getGuild())).queue();
    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Информация о версии";
    }
        /*!
    Возвращает имя команды для вызова из чата
    */

    @Override
    public String getInvoke() {
        return "version";
    }
}
