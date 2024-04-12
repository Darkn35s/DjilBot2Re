package dji.lbot2Re.Commands.Music;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;
/*!
	\brief Класс команды присоединения к голосовому чату
	\author Dmitrii Alekseev
	\version 1.0
*/
public class JoinCommand implements ICommand {
     /*!
    Обработчик команды,принимает объект типа event и обрабатываетприсоединение бота к каналу
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */

    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {

        TextChannel channel = event.getChannel().asTextChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (audioManager.isConnected()) {

            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Я уже подключена к голосовому каналу",event.getGuild())).queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Пожалуйста подключитесь к голосовому каналу",event.getGuild())).queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel().asVoiceChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            event.getMessage().replyEmbeds(BotUtils.CommonMessage(String.format("Я не могу подключиться к %s", voiceChannel),event.getGuild())).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
        event.getMessage().replyEmbeds(BotUtils.CommonMessage("Подключаюсь к вашему голосовому каналу",event.getGuild())).queue();
    }
    /*!
       Возвращает подробное описание команды для -help
       */
    @Override
    public String gethelp() {
        return "Подключить меня к вашему голосовому каналу";
    }
    /*!
    Возвращает имя команды для вызова из чата
    */
    @Override
    public String getInvoke() {
        return "join";
    }
}