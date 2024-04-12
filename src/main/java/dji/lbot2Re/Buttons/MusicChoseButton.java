package dji.lbot2Re.Buttons;

import dji.lbot2Re.Interfaces.IButtonC;
import dji.lbot2Re.music.MusicWaiter;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
/*!
	\brief Класс кнопки выбора трека
	\author Dmitrii Alekseev
	\version 1.0
*/
public class MusicChoseButton implements IButtonC {
    @Override
    public String getInvoke() {
        return "Music";
    }
    /*!
    Обработчик команды,принимает объект типа event
    \param[in] event событие запустившее обработчик
    */
    @Override
    public void handle( ButtonInteractionEvent event) {

        if(MusicWaiter.getINSTANCE().PlayOrder(event.getChannel().asTextChannel(),event.getComponentId(),event.getMessage().getIdLong())){
            event.editButton(Button.success(event.getComponentId(),"Выбрано ")).queue();
            event.getMessage().delete().queue();
        }
        else
        {
            event.editMessageEmbeds(new EmbedBuilder().appendDescription("Трек уже выбран, сделай новый запрос через -play")
                    .setColor(BotUtils.getMemColor(event.getGuild())).build()).queue();
        }

    }
}
