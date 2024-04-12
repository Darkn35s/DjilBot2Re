package dji.lbot2Re.Commands.Music;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
/*!
	\brief Класс Обработчик для создания плейлиста
	\author Dmitrii Alekseev
	\version 1.0
	\warning находится в разработке
	\bug может вызвать ошибку при отсутствующем подключении к бд
*/
public class AddPlaylistCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event и запускает сценарий создания плейлиста
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        if(args.isEmpty()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Введите название плейлиста",event.getGuild())).queue();
            return;
        }
        String name = String.join(" ",args);
        PostgreManager.getInstanse().AddPlaylist(event,name);
    }
    /*!
    Возвращает подробное описание команды для -help
    */

    @Override
    public String gethelp() {
        return "Создать локальный плейлист";
    }
    /*!
    Возвращает имя команды для вызова из чата
    */
    @Override
    public String getInvoke() {
        return "addpl";
    }
}
