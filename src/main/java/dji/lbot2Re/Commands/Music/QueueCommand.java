package dji.lbot2Re.Commands.Music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.music.GuildMusicManager;
import dji.lbot2Re.music.PlayerManager;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
/*!
	\brief Класс команды просмотра очереди
	\author Dmitrii Alekseev
	\version 1.0
*/
public class QueueCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event и отображает текущую очередб треков
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
        TextChannel channel=event.getChannel().asTextChannel();
        PlayerManager playerManager=PlayerManager.getINSTANCE();
        GuildMusicManager musicManager=playerManager.getGuildMusicMaanager(event.getGuild());
        BlockingQueue<AudioTrack> queue=musicManager.scheduler.getQueue();

        if(queue.isEmpty()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Плейлист пуст :(",event.getGuild())).queue();
            return;
        }
        int trackCnt=Math.min(queue.size(),20);
        List<AudioTrack> tracks=new ArrayList<>(queue);
        EmbedBuilder builder=new EmbedBuilder();
        builder.setTitle("Текущий плейлист");
        for(int i=0;i<trackCnt;i++){
            AudioTrack track=tracks.get(i);
            AudioTrackInfo info=track.getInfo();
            builder.appendDescription(String.format("%s - %s\n",info.title,info.author));
        }
        builder.setColor(BotUtils.getMemColor(event.getGuild()));
        event.getMessage().replyEmbeds(builder.build()).queue();
    }
     /*!
    Возвращает подробное описание команды для -help
    */

    @Override
    public String gethelp() {
        return "Просмотр плейлиста";
    }
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "queue";
    }
}
