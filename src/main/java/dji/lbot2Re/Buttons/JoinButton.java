package dji.lbot2Re.Buttons;

import dji.lbot2Re.Interfaces.IButtonC;
import dji.lbot2Re.utils.BotUtils;
import dji.lbot2Re.utils.SquidGame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
/*!
\brief Класс кнопки присоединения
\author Dmitrii Alekseev
\version 1.0
*/
public class JoinButton implements IButtonC {
    /*!
    Обработчик команды,принимает объект типа event
    \param[in] event событие запустившее обработчик
    */
    @Override
    public void handle(ButtonInteractionEvent event) {
        EmbedBuilder builder=new EmbedBuilder();
        builder.setColor(BotUtils.getMemColor(event.getGuild()));
        builder.setTitle("Игра в кальмара");
        SquidGame.getINSTANCE().addNew(event);
        for (Long id:SquidGame.getINSTANCE().kill) {
            builder.appendDescription(event.getGuild().getMemberById(id).getEffectiveName()+"\n");
        }
        event.getMessage().editMessageEmbeds(builder.build()).queue();
        event.editButton(Button.success("sqvdi","Присоединиться "+SquidGame.getINSTANCE().kill.size())).queue();
    }


    @Override
    public String getInvoke() {
        return "sqvdi";
    }
}
