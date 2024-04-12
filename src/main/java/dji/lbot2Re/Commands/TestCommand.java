package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;

import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class TestCommand implements ICommand {
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Не хватает данных `" + JillConstants.prefix + "help " + getInvoke() + "`").queue();
        }
        String Joined=String.join(" ",args).replaceFirst("@","");
        List <Member> mem=event.getGuild().getMembersByNickname(Joined,true);


        for (Member tmp:mem) {
            tmp.getUser().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage("Иди нахуй"))
                    .queue();
        }

    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Для тестов";
    }

        /*!
    Возвращает имя команды для вызова из чата
    */

    @Override
    public String getInvoke() {
        return "test";
    }
}
