package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Random;
/*!
	\brief Класс команды рулетка
	\author Dmitrii Alekseev
	\version 1.0
*/
public class RollCommand implements ICommand {
    private final Random rand=new Random();
    @Override
    public String getInvoke() {
        return "roll";
    }
    /*!
       Возвращает подробное описание команды для -help
       */

    @Override
    public String gethelp() {return "roll 1-100 по просьбе Расима";
    }
    /*!
     Возвращает имя команды для вызова из чата
     */

    /*!
    Обработчик команды,принимает объект типа event и выводит случайное число от 1 до 100
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        event.getMessage().replyEmbeds(BotUtils.CommonMessage("`"+Integer.toString(rand.nextInt(100))+"`",event.getGuild())).queue();
    }
}
