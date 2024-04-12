package dji.lbot2Re.Commands;

import dji.lbot2Re.Managers.CommandManager;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.JillConstants;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class HelpCommand implements ICommand {
    private CommandManager manager;
    private final Random rand=new Random();
    public HelpCommand(CommandManager manager){
        this.manager = manager;
    }


        /*!
    Возвращает имя команды для вызова из чата
    */
    @Override
    public String getInvoke() {
        return "help";
    }
     /*!
   Возвращает подробное описание команды для -help
   */

    @Override
    public String gethelp() {
        return "Просмотр всех доступных команд\n"+"Использование:   "+ JillConstants.prefix +getInvoke()+" нужная команда";
    }


    private Color GetRandColor(){
        float r = rand.nextFloat();
        float g= rand.nextFloat();
        float b= rand.nextFloat();
        return new Color(r,g,b);
    }

    /*!
    Обработчик команды,принимает объект типа event и выводит описание всех команд
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        if(args.isEmpty()){
            generateAndSendEmbed(event);
            return;
        }
        ICommand command=manager.getcommand(String.join("",args));
        if (command == null){
            event.getMessage().reply("Команда не обнаружена").queue();
            return;
        }
        String message = "Команда `-"+command.getInvoke()+"`: " + command.gethelp();

        event.getMessage().replyEmbeds(BotUtils.CommonMessage(message, event.getGuild())).queue();

    }

    private void generateAndSendEmbed(MessageReceivedEvent event){
        EmbedBuilder builder=new EmbedBuilder();
        builder.setTitle("Все доступные команды");
        builder.setColor(GetRandColor());
        StringBuilder descbuilder=builder.getDescriptionBuilder();
        manager.getcommands().forEach(
                (command)->descbuilder.append("`-").append(command.getInvoke()).append("`  "+command.gethelp()+"\n")
        );


        event.getMessage().replyEmbeds(builder.build()).queue();
    }

}
