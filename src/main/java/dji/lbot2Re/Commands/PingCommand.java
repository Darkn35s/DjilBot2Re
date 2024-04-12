package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.music.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/*!
	\brief Класс команды ping
	\author Dmitrii Alekseev
	\version 1.0
*/
public class PingCommand implements ICommand {

    /*!
    Обработчик команды,тестовая команда
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        event.getMember().getUser().openPrivateChannel()
                .flatMap(channel -> channel.sendMessage("pong"))
                .queue();
    }
    /*!
   Возвращает подробное описание команды для -help
   */

    @Override
    public String gethelp() {
        return "Standart app response test";
    }

    /*!
    Возвращает имя команды для вызова из чата
    */

    @Override
    public String getInvoke() {
        return "ping";
    }
}
