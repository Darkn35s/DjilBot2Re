package dji.lbot2Re.Commands.Music;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.utils.JillConstants;
import dji.lbot2Re.music.PlayerManager;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
/*!
	\brief Класс команды play
	\author Dmitrii Alekseev
	\version 1.0
*/
public class PlayCommand implements ICommand {
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "play";
    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Добавить трек или плейлист `"+ JillConstants.prefix +"play https://www.youtube.com/watch?v=dQw4w9WgXcQ`";
    }
    private boolean isUrl(String input){
        try {
            new URL(input);
            return true;
        }catch (MalformedURLException ignored){
            return false;
        }

    }
    /*!
    Обработчик команды,принимает объект типа event и ставит плейлист или трек по ссылке Url
    \param[in] event событие запустившее обработчик
    \param[in] args набор параметров передаваемых в команду(Поисковой запрос или сссылка url)
    */
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {
        Member self= event.getGuild().getSelfMember();
        GuildVoiceState selfVoice=self.getVoiceState();
        PostgreManager db= PostgreManager.getInstanse();
        if(!event.getMember().getVoiceState().inAudioChannel()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Вы должны быть подключены к голосовому каналу_",event.getGuild())).queue();
            return;
        }
        if (selfVoice.inAudioChannel()) {
            if (!selfVoice.getChannel().asVoiceChannel().equals(event.getMember().getVoiceState().getChannel().asVoiceChannel())) {
                event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Мы должны быть в одном голосовом канале_",event.getGuild())).queue();
                return;
            }
        }
        TextChannel txt=event.getChannel().asTextChannel();
        if(args.isEmpty()){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("Пожалуйста укажи ссылку на YT",event.getGuild())).queue();
            return;
        }
        String input = String.join(" ",args);
        if(!isUrl(input)){
            input="ytsearch:"+input;

        }
        db.AddUser(event.getMember().getUser());
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel().asVoiceChannel());
        PlayerManager manager = PlayerManager.getINSTANCE();
        event.getMessage().delete().queue();
        manager.LoadAndPlay(event.getChannel().asTextChannel(), input,isUrl(input));
        manager.getGuildMusicMaanager(event.getGuild()).player.setVolume(100);
    }
}
