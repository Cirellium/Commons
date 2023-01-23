package net.cirellium.commons.bukkit.command.abstraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.abstraction.result.CommandOutcome;
import net.cirellium.commons.bukkit.command.abstraction.result.CommandResult;
import net.cirellium.commons.bukkit.service.AbstractBukkitService;
import net.cirellium.commons.common.service.ServiceType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public abstract class AbstractCommandManager<
        P extends CirelliumBukkitPlugin<P>,               // The plugin class that extends CirelliumPlugin
        H,                                          // The command handler class that extends this class
        M extends AbstractCommandManager<P, H, M>   // The actual command manager that extends this class
        > extends AbstractBukkitService<P> {

    protected final P plugin;
    protected final CommandHandler<P, H, M> commandHandler;

    protected final HashMap<String, AbstractCommand<P, M>> commands;

    public AbstractCommandManager(P plugin) {
        super(plugin, 
            ServiceType.COMMAND, 
            true, 
            new ServiceType[] { ServiceType.DATABASE, ServiceType.FILE, ServiceType.CACHE });
        this.plugin = plugin;
        this.commandHandler = new CommandHandler<P, H, M>(this);

        this.commands = new HashMap<>();
    }

    public AbstractCommandManager(P plugin, CommandHandler<P, H, M> handler, ServiceType type, boolean autoInitialize, ServiceType[] dependencies) {
        super(plugin, 
            ServiceType.COMMAND, 
            true, 
            new ServiceType[] { ServiceType.DATABASE, ServiceType.FILE, ServiceType.CACHE });
        this.plugin = plugin;
        this.commandHandler = handler;

        this.commands = new HashMap<>();
    }

    @Override
    public void initialize() {
        

    }

    @Override
    public void shutdown() {
        
    }

    protected CommandResult executePlayer(Player player, org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) return getCommand("").executePlayer(null, command, args);

        if (commands.get(args[0]) == null) return new CommandResult(CommandOutcome.ERROR_NO_COMMAND_FOUND);

        for (String commandName : commands.keySet()) { // ! TODO Untested
            if (!(args[0].equalsIgnoreCase(commandName))) continue;

            AbstractCommand<P, M> cmd = commands.get(commandName);

            if (cmd.isDirectCommand()) return cmd.executePlayer(null, command, args);

            if (args.length == 1) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND, Component.text("Please provide a subcommand", TextColor.fromHexString("#ff523b")));

            if (!cmd.hasSubCommand(args[1])) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND_FOUND, Component.text("No subcommand was found for " + commandName, TextColor.fromHexString("#ff523b")));

            return cmd.getSubCommand(args[1]).executePlayer(null, command, args);

            // for (String subCommandName : cmd.getSubCommands().keySet()) {
            //     if (!(args[1].equalsIgnoreCase(subCommandName))) continue;
                
            //     return cmd.getSubCommands().get(subCommandName).executePlayer(null, command, args);
            // }
        }
        return new CommandResult(CommandOutcome.ERROR, Component.text("An unknown error occurred whilst trying to execute command", TextColor.fromHexString("#ff523b")));
    }

    protected CommandResult executeConsole(org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) {
            return new CommandResult(CommandOutcome.ERROR_NO_COMMAND, Component.text("Please provide a command", TextColor.fromHexString("#ff523b")));
        }

        if (commands.get(args[0]) == null) return new CommandResult(CommandOutcome.ERROR_NO_COMMAND_FOUND, Component.text("No command was found for " + args[0], TextColor.fromHexString("#ff523b")));
        
        for (String commandName : commands.keySet()) { // ! TODO Untested
            if (!(args[0].equalsIgnoreCase(commandName))) continue;

            AbstractCommand<P, M> cmd = commands.get(commandName);

            if (cmd.isDirectCommand()) return cmd.executeConsole(command, args);

            if (args.length == 1) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND, Component.text("Please provide a subcommand", TextColor.fromHexString("#ff523b")));

            if (!cmd.hasSubCommand(args[1])) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND_FOUND, Component.text("No subcommand was found for " + commandName, TextColor.fromHexString("#ff523b")));

            return cmd.getSubCommand(args[1]).executeConsole(command, args);
        }
        return new CommandResult(CommandOutcome.ERROR, Component.text("An unknown error occurred whilst trying to execute command", TextColor.fromHexString("#ff523b")));
    }

    public List<AbstractCommand<P, M>> getCommands() {
        return new ArrayList<AbstractCommand<P, M>>(commands.values());
    }

    public abstract HashMap<String, AbstractCommand<P, M>> getCommandMap();

    public AbstractCommand<P, M> getCommand(String commandName) {
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

    public CommandHandler<P, H, M> getCommandHandler() {
        return commandHandler;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}