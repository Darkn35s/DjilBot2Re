package dji.lbot2Re.Managers;

import dji.lbot2Re.Buttons.JoinButton;
import dji.lbot2Re.Buttons.MusicChoseButton;
import dji.lbot2Re.Buttons.StartSqButton;
import dji.lbot2Re.Interfaces.IButtonC;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.music.MusicWaiter;
import dji.lbot2Re.utils.BotUtils;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ButtonManager {
    /*!
	\brief Класс Обработчик для обрабтки кнопок
	\author Dmitrii Alekseev
	\version 1.0

*/
    private final Map<String, IButtonC> ButtonsMap=new HashMap<>();
    public ButtonManager(){

        addCommand(new MusicChoseButton());
        addCommand(new JoinButton());
        addCommand(new StartSqButton());
    }

    private void addCommand(IButtonC button) {
        if(!ButtonsMap.containsKey(button.getInvoke())){
            ButtonsMap.put(button.getInvoke(),button);
        }
    }

    public void handleCommand(ButtonInteractionEvent event){
        if(ButtonsMap.containsKey(event.getComponentId().substring(0,5))){
            ButtonsMap.get(event.getComponentId().substring(0,5)).handle(event);
        }
    }

}
