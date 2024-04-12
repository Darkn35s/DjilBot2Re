package dji.lbot2Re.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dji.lbot2Re.Entities.MusicOrder;
import dji.lbot2Re.utils.BotUtils;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.HashMap;
import java.util.Map;
/*!
	\brief Класс Обработчик для управления плеером
	\author Dmitrii Alekseev
	\version 1.0

*/

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private AudioPlayerManager playerManager;
    private Map<Long,GuildMusicManager> musicManagers;


    PlayerManager(){
        this.musicManagers=new HashMap<>();

        this.playerManager=new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicMaanager(Guild guild){
        long guilid=guild.getIdLong();
        GuildMusicManager musicManager=musicManagers.get(guilid);
        if (musicManager==null){
            musicManager=new GuildMusicManager(playerManager);
            musicManagers.put(guilid,musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }


    public void LoadAndPlay(TextChannel channel,String track_url,Boolean isUrl){
        GuildMusicManager musicManager=getGuildMusicMaanager(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, track_url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                channel.getGuild().getChannelById(MessageChannel.class, JillConstants.logsId).sendMessage("Добавила трек в очередь: "+audioTrack.getInfo().title).queue();
                play(musicManager,audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                if (isUrl){
                    AudioTrack firstTrack = audioPlaylist.getSelectedTrack();
                    if (firstTrack == null) {
                        firstTrack = audioPlaylist.getTracks().remove(0);
                    }
                    channel.getGuild().getChannelById(MessageChannel.class, JillConstants.logsId).sendMessage("Добавила плейлист:  " + audioPlaylist.getName() + " в очередь. Первый трек:  " + firstTrack.getInfo().title).queue();
                    play(musicManager, firstTrack);
                    audioPlaylist.getTracks().forEach(musicManager.scheduler::queue);
                }
                else {
                    EmbedBuilder builder=new EmbedBuilder();
                    builder.setTitle("Нашла по вашему запросу");
                    builder.setColor(BotUtils.getMemColor(channel.getGuild()));
                    StringBuilder descbuilder=builder.getDescriptionBuilder();
                    for(int i=0;i<Math.min(5,audioPlaylist.getTracks().size());i++){
                        builder.appendDescription("`"+(i+1)+"`"+": "+audioPlaylist.getTracks().get(i).getInfo().title+"\n");
                    }

                    channel.sendMessageEmbeds(builder.build()).addActionRow(
                            Button.primary("Music1", "1"),
                            Button.primary("Music2", "2"),
                            Button.primary("Music3", "3"),
                            Button.primary("Music4", "4"),
                            Button.primary("Music5", "5")
                    ).queue((message) -> {
                        MusicWaiter.getINSTANCE().NewOrder(new MusicOrder(message.getIdLong(),audioPlaylist.getTracks().subList(0,5)));
                    });
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Ничего не нашла по запросу: "+track_url).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("ОЙ что-то не так: "+e.getMessage()).queue();
            }
        });
    }
    private void play(GuildMusicManager musicManager, AudioTrack track){
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getINSTANCE(){
        if (INSTANCE==null){
            INSTANCE=new PlayerManager();
        }
        return INSTANCE;
    }
}
