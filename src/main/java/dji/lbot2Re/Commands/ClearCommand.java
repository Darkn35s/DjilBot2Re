package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
/*!
	\brief Класс команды очистки чата
	\author Dmitrii Alekseev
	\version 1.0
*/
public class ClearCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event и очищает историю сообщений
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        event.getChannel().asTextChannel().getIterableHistory().takeAsync(5).thenApplyAsync((messages)->{

            return 0;
        });
    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "отчистка чата";
    }
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "clear";
    }
}
