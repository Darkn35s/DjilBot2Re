package dji.lbot2Re;

import com.sedmelluq.discord.lavaplayer.source.youtube.DefaultYoutubePlaylistLoader;
import dji.lbot2Re.Managers.ButtonManager;
import dji.lbot2Re.Managers.CommandManager;
import dji.lbot2Re.eventhandle.EventHandlers;
import dji.lbot2Re.telegram.DjillTG;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/*!
	\brief Main Class
	\author Dmitrii Alekseev
	\version 1.0
	\warning Main класс
*/

public class Main
{
    private static ShardManager shard;


    public static void main(String[] args) {
        CommandManager manager=new CommandManager();
        ButtonManager Bmanager=new ButtonManager();
        EventHandlers eventHandlers= new EventHandlers(manager,Bmanager);
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(JillConstants.token).enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.competing("Казуалов"));
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
        DjillTG tg= DjillTG.getTg();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(tg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        shard=builder.build();
        shard.addEventListener(eventHandlers);


    }



}
