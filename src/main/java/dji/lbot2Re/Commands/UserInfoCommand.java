package dji.lbot2Re.Commands;

import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.BotUtils;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoCommand implements ICommand {
    @Override
    public void handle(List<String> args, MessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Не хватает данных `" + JillConstants.prefix + "help " + getInvoke() + "`").queue();
        }
        String Joined=String.join(" ",args).replaceFirst("@","");
        List <Member> mem=event.getGuild().getMembersByNickname(Joined,true);


        for (Member tmp:mem) {
            EmbedBuilder builder=new EmbedBuilder().setColor((tmp.getColor()))
                    .setThumbnail(tmp.getEffectiveAvatarUrl().replaceFirst("gif","png"))
                    .addField("Имя пользователя", String.format("%#s", tmp.getUser()), false)
                    .addField("Отображаемое имя", tmp.getEffectiveName(), false)
                    .addField("ID + Mention", String.format("%s (%s)", tmp.getUser().getId(), tmp.getAsMention()), false)
                    .addField("Создан", tmp.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
                    .addField("Присоединился", tmp.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
                    .addField("Бот", tmp.getUser().isBot() ? "Yes" : "No", false);
            event.getChannel().asTextChannel().sendMessageEmbeds(builder.build()).queue();
        }

    }
    /*!
   Возвращает подробное описание команды для -help
   */
    @Override
    public String gethelp() {
        return "Посмотреть информацию о пользователе";
    }

        /*!
    Возвращает имя команды для вызова из чата
    */

    @Override
    public String getInvoke() {
        return "userinfo";
    }
}