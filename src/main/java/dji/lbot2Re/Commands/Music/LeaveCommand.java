package dji.lbot2Re.Commands.Music;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;
/*!
	\brief Класс отключения от голосового чата
	\author Dmitrii Alekseev
	\version 1.0
*/
public class LeaveCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event и отключает бота от голосового канала
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        Member self= event.getGuild().getSelfMember();
        GuildVoiceState selfVoice=self.getVoiceState();
        if(!selfVoice.inAudioChannel()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Я должна быть подключена к голосовому каналу, используй `join`_",event.getGuild())).queue();
            return;
        }
        if(!event.getMember().getVoiceState().inAudioChannel()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Вы должны быть подключены к голосовому каналу_",event.getGuild())).queue();

            return;
        }
        if(!selfVoice.getChannel().asVoiceChannel().equals(event.getMember().getVoiceState().getChannel().asVoiceChannel())){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Мы должны быть в одном голосовом канале_",event.getGuild())).queue();
            return;
        }
        TextChannel channel = event.getChannel().asTextChannel();
        AudioManager audioManager= event.getGuild().getAudioManager();
        VoiceChannel voiceChannel = audioManager.getConnectedChannel().asVoiceChannel();
        audioManager.closeAudioConnection();
        event.getMessage().replyEmbeds(BotUtils.CommonMessage("Отключаюсь от голосового канала",event.getGuild())).queue();
    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Отключиться от голосового канала";
    }
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "leave";
    }
}
