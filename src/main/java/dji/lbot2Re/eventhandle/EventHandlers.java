package dji.lbot2Re.eventhandle;
import dji.lbot2Re.Managers.ButtonManager;
import dji.lbot2Re.Managers.CommandManager;
import dji.lbot2Re.PSDBctrl.PostgreManager;
import dji.lbot2Re.music.MusicWaiter;
import dji.lbot2Re.telegram.DjillTG;
import dji.lbot2Re.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class EventHandlers extends ListenerAdapter {
    private  CommandManager manager;
    private  ButtonManager Bmanager;
    public EventHandlers(CommandManager manager, ButtonManager Bmanager){
        this.manager=manager;
        this.Bmanager=Bmanager;
    }

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        if(event.getUser().getId().equals("475592768639467520")&&!event.getNewNickname().toLowerCase().equals("casual")){
            event.getMember().modifyNickname("Casual").queue();
            event.getGuild().getTextChannelById(1188259537510219827L).sendMessage(event.getUser().getAsMention()+ " Cекиро пройди сначала казуал").queue();
        }

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
          if(event.getAuthor().isBot())
              return;
          if(event.getChannel().asTextChannel().getIdLong()==1163809981783617557L||1163867462576640000L==event.getChannel().asTextChannel().getIdLong()){
              List<Message.Attachment>atch= event.getMessage().getAttachments();
              if(!atch.isEmpty()){
                  for (Message.Attachment atc:atch
                       ) {
                      if (atc.isImage()){
                          String f= atc.getUrl();
                          DjillTG.getTg().send(f);
                      }
                  }

                  /*
                   String f = "temp."+ atch.get(0).getFileExtension();
                   atch.get(0).downloadToFile("temp."+ atch.get(0).getFileExtension());
                  DjillTG.getTg().send("temp."+ atch.get(0).getFileExtension());
                  */
              }
          }
          manager.handleCommand(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Bmanager.handleCommand(event);
    }


    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        PostgreManager.getInstanse().AddUser(event.getUser());
    }
}
