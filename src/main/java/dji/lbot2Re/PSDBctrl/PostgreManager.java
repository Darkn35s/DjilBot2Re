package dji.lbot2Re.PSDBctrl;
import dji.lbot2Re.Entities.MusicOrder;
import dji.lbot2Re.utils.BotUtils;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
/*!
	\brief Класс Ообертка для взаимодествия с бд
	\author Dmitrii Alekseev
	\version 1.0
	\warning находится в разработке
	\bug может вызвать ошибку при отсутствующем подключении к бд
*/
public class PostgreManager {
        private static PostgreManager INSTANSE;
        private Connection conn;
        private PostgreManager(){
            try{
                conn = DriverManager.getConnection(JillConstants.ConnDb);
                System.out.println("Connected to DB on port:5432");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        public void AddUser(User user){


            try{
                Statement st=conn.createStatement();
                ResultSet re= st.executeQuery("SELECT count(*) FROM Users Where did="+user.getId());
                re.next();
                int result = Integer.parseInt(re.getString(1));
                if(result==0){
                    PreparedStatement pst=conn.prepareStatement("INSERT INTO Users(did, UserName) VALUES(?,?)");
                    pst.setLong(1,user.getIdLong());
                    pst.setString(2,user.getEffectiveName());
                    pst.executeUpdate();
                    pst.close();
                }
                st.close();

            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        public void AddPlaylist(MessageReceivedEvent event, String name){
            User user=event.getMember().getUser();
            try{
                    PreparedStatement pst=conn.prepareStatement("INSERT INTO Playlists (userid,pname) VALUES(?,?)");
                    pst.setLong(1,user.getIdLong());
                    pst.setString(2,name);
                    pst.executeUpdate();
                    pst.close();
                    event.getMessage().replyEmbeds(BotUtils.CommonMessage("Плейлист создан",event.getGuild())).queue();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                event.getMessage().replyEmbeds(BotUtils.CommonMessage("Не могу создать плейлист(",event.getGuild())).queue();
            }
        }
    public StringSelectMenu.Builder AddToPlaylist(MessageReceivedEvent event){
        User user=event.getMember().getUser();
        try{
            Statement st=conn.createStatement();
            ResultSet re= st.executeQuery("SELECT did,pname FROM Playlists Where userid="+user.getId());
            Map<Long, MusicOrder> Orders = new HashMap<>();
            StringSelectMenu.Builder builder=StringSelectMenu.create("Список плейлистов");
            if(re.next()){
                builder.addOption(re.getString(2),re.getString(1));
                while (re.next()){
                    builder.addOption(re.getString(2),re.getString(1));
                }
                st.close();
                return builder;
            }
            else{
                st.close();
                return null;
            }



        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
        public static PostgreManager getInstanse(){
            if(INSTANSE==null){
                INSTANSE=new PostgreManager();
            }
            return INSTANSE;
        }



}
