package dji.lbot2Re.Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.music.GuildMusicManager;
import dji.lbot2Re.music.PlayerManager;
import dji.lbot2Re.music.TrackScheduler;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.List;
import java.util.concurrent.TimeUnit;
/*!
	\brief Класс команды текущего трека
	\author Dmitrii Alekseev
	\version 1.0
*/
public class NowPlayingCommand implements ICommand {
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "nowplay";
    }
    /*!
       Возвращает подробное описание команды для -help
       */
    @Override
    public String gethelp() {
        return "Посмотреть текущий трек";
    }

    /*!
    Обработчик команды,принимает объект типа event и отображает текущий трек
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
        StringSelectMenu.Builder ar=PostgreManager.getInstanse().AddToPlaylist(event);
        EmbedBuilder builder = new EmbedBuilder();
        AudioTrackInfo info=player.getPlayingTrack().getInfo();
        builder.setTitle("Текущая композиция");
        builder.appendDescription(info.title+"\n");
        builder.appendDescription(info.author+"\n");
        builder.appendDescription(info.uri+"\n");
        builder.appendDescription(String.format("%s %s - %s+\n",player.isPaused()?"⏸":"▶",timeForm(player.getPlayingTrack().getPosition()),timeForm(player.getPlayingTrack().getDuration())));
        builder.setColor(BotUtils.getMemColor(event.getGuild()));
        if(ar==null) {
            event.getMessage().replyEmbeds(builder.build()).queue();
        }
        else {
            builder.appendDescription("Добавить в плейлист");
            event.getMessage().replyEmbeds(builder.build()).addActionRow(ar.build()).queue();
        }
    }
    public  String timeForm(long timeMs){
        final long hours=timeMs/ TimeUnit.HOURS.toMillis(1);
        final long minute=timeMs/ TimeUnit.MINUTES.toMillis(1);
        final long seconds=timeMs% TimeUnit.MINUTES.toMillis(1)/TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d",hours,minute,seconds);

    }
}
