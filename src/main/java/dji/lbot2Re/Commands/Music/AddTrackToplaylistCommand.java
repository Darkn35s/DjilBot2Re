package dji.lbot2Re.Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.music.GuildMusicManager;
import dji.lbot2Re.music.PlayerManager;
import dji.lbot2Re.music.TrackScheduler;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
/*!
	\brief  Класс обработчик добавления треков в плейлисты
	\author Dmitrii Alekseev
	\version 1.0
	\warning находится в разработке
	\bug может вызвать ошибку при отсутствующем подключении к бд
*/
public class AddTrackToplaylistCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event и обрабатывает добавление трека в плейлист
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
        TextChannel txt=event.getChannel().asTextChannel();
        PlayerManager playerManager=PlayerManager.getINSTANCE();
        GuildMusicManager musicManager = playerManager.getGuildMusicMaanager(event.getGuild());
        TrackScheduler scheduler= musicManager.scheduler;
        AudioPlayer player = musicManager.player;
        if(player.getPlayingTrack()==null){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Плейлист пуст(",event.getGuild())).queue();
            return;
        }
        PostgreManager.getInstanse().AddToPlaylist(event);
    }

    /*!
    Возвращает подробное описание команды для -help
    */

    @Override
    public String gethelp() {
        return "Добавить текущий трек в локальный плейлист";
    }
    /*!
       Возвращает имя команды для вызова из чата
    */
    @Override
    public String getInvoke() {
        return "addtopl";
    }
}
