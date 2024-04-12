package dji.lbot2Re.music;

import dji.lbot2Re.Entities.MusicOrder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*!
	\brief Класс Обработчик для ожидания выбора музыки через меню реализовано через синглтон
	\author Dmitrii Alekseev
	\version 1.0
    \exmple MusicWaiter music = MusicWaiter.getINSTANSE();
*/

public class MusicWaiter
{
    private static MusicWaiter INSTANCE;
    private Map<Long,MusicOrder> Orders = new HashMap<>();
    private List<Long> onDel=new LinkedList<>();
    public void NewOrder(MusicOrder order){
        onDel.clear();
        for( Map.Entry<Long,MusicOrder> temp:Orders.entrySet()){
            temp.getValue().gen++;
            if (temp.getValue().gen==20){
                onDel.add(temp.getKey());
            }
        }
        for (Long id:onDel) {
            Orders.remove(id);
        }

        Orders.put(order.messageId,order);
    }

    public boolean PlayOrder(TextChannel channel,String choice,Long id){
        if (Orders.containsKey(id)) {
            int track = Integer.parseInt(choice.substring(5))-1;
            PlayerManager.getINSTANCE().LoadAndPlay(channel, INSTANCE.Orders.get(id).Tracks.get(track).getInfo().uri, true);
            INSTANCE.Orders.remove(id);
            return true;
        }
        return false;
    }
    public static synchronized MusicWaiter getINSTANCE(){
        if (INSTANCE==null){
            INSTANCE=new MusicWaiter();
        }
        return INSTANCE;
    }

}
