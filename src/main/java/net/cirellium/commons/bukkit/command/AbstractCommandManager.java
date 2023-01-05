package net.cirellium.commons.bukkit.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.service.AbstractService;
import net.cirellium.commons.common.service.ServiceType;

@SuppressWarnings("unchecked")
public abstract class AbstractCommandManager<
        P extends CirelliumPlugin<P>, // The plugin class that extends CirelliumPlugin
        H,   // The command handler class that extends this class
        M extends AbstractCommandManager<P, H, M>
        > extends AbstractService<P> {

    protected final P plugin;
    protected final M thisManager;
    protected final H commandHandler;

    protected final HashMap<String, AbstractCommand<P, M>> commands;

    public AbstractCommandManager(P plugin, H handler, ServiceType type, boolean autoInitialize, ServiceType[] dependencies) {
        super(plugin, 
            ServiceType.COMMAND, 
            true, 
            new ServiceType[] { ServiceType.DATABASE, ServiceType.FILE, ServiceType.CACHE });
        this.plugin = plugin;
        this.commandHandler = handler;
        this.thisManager = (M) this;


        this.commands = new HashMap<>();
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void shutdown() {
        
    }

    protected void executeCommand(Player player, org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) {
            getCommand("").executePlayer(null, command, args);
            return;
        }

        commands.keySet().forEach(commandName -> {
            if (args[0].equalsIgnoreCase(commandName)) {

                AbstractCommand<P, M> cmd = commands.get(commandName);
                


                if (isDirectCommand(cmd)) {
                    cmd.executePlayer(null, command, args);
                    return;
                }

                if (args.length == 1) {
                    player.sendMessage("§cBitte gib einen SubCommand an!");
                    return;
                }

                cmd.getSubCommands().keySet().forEach(subCommand -> {
                    if (args[1].equalsIgnoreCase(subCommand)) {
                        cmd.getSubCommands().get(subCommand).executePlayer(null, command, args);
                    }
                });
            }
        });
    }

    protected void executeConsole(org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) {
            Bukkit.getConsoleSender().sendMessage("§cBitte gib einen Befehl an!");
            return;
        }

        commands.keySet().forEach(commandName -> {
            if (commandName.equalsIgnoreCase(args[0])) {
                commands.get(commandName).executeConsole(command, args);
            }
        });
        logger.warning("&cNo command matching " + args[0] + " found!");
    }

    public List<AbstractCommand<P, M>> getCommands() {
        return new ArrayList<AbstractCommand<P, M>>(commands.values());
    }

    public HashMap<String, AbstractCommand<P, M>> getCommandMap() {
        return commands;
    }

    public AbstractCommand<P, ?> getCommand(String commandName) {
        return commands.get(commandName);
    }

    public void registerCommand(AbstractCommand<P, M> command) {
        commands.put(command.getCommandName(), command);
    }

    public boolean isSubCommand(AbstractCommand<P, M> command) {
        return command instanceof SubCommand;
    }

    public boolean isDirectCommand(AbstractCommand<P, M> command) {
        return !isSubCommand(command);
    }

    public H getCommandHandler() {
        return commandHandler;
    }
    
}