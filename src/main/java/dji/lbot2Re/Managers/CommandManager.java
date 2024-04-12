package dji.lbot2Re.Managers;

import dji.lbot2Re.Commands.*;
import dji.lbot2Re.Commands.Music.*;
import dji.lbot2Re.Interfaces.ICommand;
import dji.lbot2Re.utils.JillConstants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;
/*!
	\brief Класс Обработчик для обработки комманд
	\author Dmitrii Alekseev
	\version 1.0

*/
public class CommandManager {
    private final Map<String, ICommand> commandsMap=new HashMap<>();
    public CommandManager(){
        addCommand((new PingCommand()));
        addCommand((new HelpCommand(this)));
        addCommand(new RollCommand());
        addCommand(new LeaveCommand());
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new QueueCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new RepeatCommand());
        addCommand(new UnpauseCommand());
        addCommand(new PauseCommand());
        addCommand(new UserInfoCommand());
        addCommand(new SquidGameCommand());
        addCommand(new PlayerCommand());
        addCommand(new TestCommand());
        addCommand(new AddPlaylistCommand());
        addCommand(new VersionCommand());
    }
    private void addCommand(ICommand command) {
        if(!commandsMap.containsKey(command.getInvoke())){
            commandsMap.put(command.getInvoke(),command);
        }
    }

    public ICommand getcommand(String name){
        return  commandsMap.get(name);
    }
    public Collection<ICommand> getcommands(){
        return commandsMap.values();
    }

    public void handleCommand(MessageReceivedEvent event){
        if(event.getMessage().getContentDisplay().startsWith(JillConstants.prefix)) {
            final String[] split = event.getMessage().getContentDisplay().replaceFirst(Pattern.quote(JillConstants.prefix), "").split(" ");
            final String invoke = split[0].toLowerCase();

            if (commandsMap.containsKey(invoke)) {

                final List<String> args = Arrays.asList(split).subList(1, split.length);
                commandsMap.get(invoke).handle(args, event);
            }
        }
    }
}
