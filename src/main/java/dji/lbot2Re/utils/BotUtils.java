package dji.lbot2Re.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Random;

public class BotUtils {
    static Random rand = new Random();

    public static Color getMemColor(Guild guild){
        return guild.getSelfMember().getColor();
    }

    public static Color getRandColor(){
        final float r= rand.nextFloat();
        final float g= rand.nextFloat();
        final float b= rand.nextFloat();
        return new Color(r,g,b);
    }

    public static MessageEmbed CommonMessage(String txt,Guild guild){
        EmbedBuilder builder=new EmbedBuilder().appendDescription(txt).setColor(getMemColor(guild));
        return builder.build();
    }


}
