package net.cirellium.commons.bukkit.command.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.abstraction.result.CommandResult;

public class CommandHandler<
    P extends CirelliumBukkitPlugin<P>, 
    H, 
    M extends AbstractCommandService<P, H, M>
    > implements CommandExecutor, TabCompleter {

    public static final BiPredicate<CommandSender, ? super AbstractCommand<?, ?>> HAS_PERMISSION = (s, c) -> s.hasPermission(c.getPermission());

    private AbstractCommandService<P, H, M> manager; 

    public CommandHandler(AbstractCommandService<P, H, M> AbstractCommandService) {
        this.manager = AbstractCommandService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandResult result = 
                (sender instanceof Player player) ? 
                    manager.executePlayer(player, command, args) : 
                    (sender instanceof ConsoleCommandSender) ? manager.executeConsole(command, args) :
                    CommandResult.INVALID_SENDER;

        if(result.isSuccess()) return true;

        if (result.getMessage() == null) {
            manager.getLogger().warning("Command was not successful, but no message was provided");
            return false;
        }

        sender.sendMessage(result.getMessage().toString());

        return result.shouldDisplayUsage();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command unused, String alias, String[] args) {
        List<String> completes = new ArrayList<String>();
        List<AbstractCommand<P, M>> cmds = new ArrayList<>(manager.getCommands());

        String commandName = args[0];

        if (args.length == 0) {
            cmds.stream()
                .filter(cmd -> cmd.matches(commandName))
                .forEach(cmd -> completes.addAll(cmd.tabComplete(sender, args)));

            manager.getLogger().info("Returning completes: " + completes);

            return completes.stream().distinct().collect(Collectors.toList());
            // for (AbstractCommand<P, M> command : manager.getCommands()) {
            //     if (command.matches(commandName)) completes.addAll(command.tabComplete(sender, args));
            // }
            // // CustomMusic.debug("Returning completes: " + completes);
            // return completes;
        }

        // for (AbstractCommand<P, M> command : manager.getCommands()) {
        //     if (command.hasPermissions(sender)) cmds.add(command.getCommandName());
        // }
        // for (String string : cmds) {
        //     if (string.toLowerCase().startsWith(commandName.toLowerCase())) completes.add(string);
        // }

        cmds.stream()
            .filter(cmd -> sender.hasPermission(cmd.getPermission()))
            .filter(cmd -> cmd.getCommandName().toLowerCase().startsWith(commandName))
            .distinct()
            .map(cmd -> cmd.getCommandName())
            .collect(Collectors.toList())
            .forEach(completes::add);

        manager.getLogger().info("Returning completes 2: " + completes);
        
        return completes;
    }

}