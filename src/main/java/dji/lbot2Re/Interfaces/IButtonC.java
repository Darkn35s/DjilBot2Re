package dji.lbot2Re.Interfaces;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface IButtonC {
    void handle(ButtonInteractionEvent event);

    String getInvoke();
}
