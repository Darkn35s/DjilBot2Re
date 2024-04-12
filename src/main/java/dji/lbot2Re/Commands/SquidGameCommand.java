package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class SquidGameCommand implements ICommand {


    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        EmbedBuilder builder=new EmbedBuilder();
        builder.setColor(BotUtils.getMemColor(event.getGuild()));
        builder.setTitle("Игра в кальмара");
        event.getChannel().asTextChannel().sendMessageEmbeds(builder.build()).addActionRow(Button.danger("sqvdi","Присоединиться"),Button.primary("sqvds","Начать")).queue();
    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Игра в кальмара";
    }
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "squidgame";
    }
}
