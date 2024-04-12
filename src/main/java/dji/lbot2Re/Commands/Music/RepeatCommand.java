package dji.lbot2Re.Commands.Music;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.music.GuildMusicManager;
import dji.lbot2Re.music.PlayerManager;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
/*!
	\brief Класс команды повтора
	\author Dmitrii Alekseev
	\version 1.0
*/
public class RepeatCommand implements ICommand {
    /*!
    Обработчик команды,принимает объект типа event изменяет режим повтора all,on, off
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
        if(args.isEmpty()||args.size()!=1){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Используйте_(**on**,**all**,**off**)",event.getGuild())).queue();
            return;
        }
        if (args.get(0).toLowerCase().equals("all")&&args.get(0).toLowerCase().equals("off")&&args.get(0).toLowerCase().equals("on")){
            event.getMessage().replyEmbeds(BotUtils.CommonMessage("_Используйте_(**on**,**all**,**off**)",event.getGuild())).queue();
            return;
        }
        GuildMusicManager musicManager= PlayerManager.getINSTANCE().getGuildMusicMaanager(event.getGuild());
        if(args.get(0).toLowerCase().equals("all")){
            musicManager.scheduler.reapitingAll=true;
            musicManager.scheduler.repeating=false;
        }
        if(args.get(0).toLowerCase().equals("on")){
            musicManager.scheduler.reapitingAll=false;
            musicManager.scheduler.repeating=true;
        }
        if(args.get(0).toLowerCase().equals("off")){
            musicManager.scheduler.reapitingAll=false;
            musicManager.scheduler.repeating=false;
        }
        event.getMessage().replyEmbeds(BotUtils.CommonMessage(String.format("Выбран режим повтора **%s**",args.get(0).toLowerCase()),event.getGuild())).queue();


    }
     /*!
    Возвращает подробное описание команды для -help
    */

    @Override
    public String gethelp() {
        return "_Повтор плейлиста_(**on**,**all**,**off**)";
    }
    /*!
Возвращает имя команды для вызова из чата
*/
    @Override
    public String getInvoke() {
        return "repeat";
    }
}
