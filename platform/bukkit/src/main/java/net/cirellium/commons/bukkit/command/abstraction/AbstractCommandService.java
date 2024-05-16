package net.cirellium.commons.bukkit.command.abstraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.service.AbstractBukkitService;
import net.cirellium.commons.common.command.result.CommandOutcome;
import net.cirellium.commons.common.command.result.CommandResult;
import net.cirellium.commons.common.service.ServiceType;

public abstract class AbstractCommandService<
        H,                                          // The command handler class that extends this class
        M extends AbstractCommandService<H, M>   // The actual command manager that extends this class
        > extends AbstractBukkitService {

    protected final CirelliumBukkitPlugin plugin;
    protected final CommandHandler<H, M> commandHandler;

    protected final HashMap<String, AbstractCommand<M>> commands;

    public AbstractCommandService(CirelliumBukkitPlugin plugin) {
        super(plugin, 
            ServiceType.COMMAND, 
            true, 
            new ServiceType[] { ServiceType.DATABASE, ServiceType.CACHE });
        this.plugin = plugin;
        this.commandHandler = new CommandHandler<H, M>(this);

        this.commands = new HashMap<>();
    }

    public AbstractCommandService(CirelliumBukkitPlugin plugin, CommandHandler<H, M> handler, ServiceType type, boolean autoInitialize, ServiceType[] dependencies) {
        super(plugin, 
            ServiceType.COMMAND, 
            true, 
            new ServiceType[] { ServiceType.DATABASE, ServiceType.CACHE });
        this.plugin = plugin;
        this.commandHandler = handler;

        this.commands = new HashMap<>();
    }

    @Override
    public void initialize(CirelliumBukkitPlugin plugin) {
        

    }

    @Override
    public void shutdown(CirelliumBukkitPlugin plugin) {
        
    }

    protected CommandResult executePlayer(Player player, org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) return getCommand("").executePlayer(null, command, args);

        if (commands.get(args[0]) == null) return new CommandResult(CommandOutcome.ERROR_NO_COMMAND_FOUND);

        for (String commandName : commands.keySet()) {
            if (!(args[0].equalsIgnoreCase(commandName))) continue;

            AbstractCommand<M> cmd = commands.get(commandName);

            if (cmd.isDirectCommand()) return cmd.executePlayer(null, command, args);

            if (args.length == 1) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND);

            if (!cmd.hasSubCommand(args[1])) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND_FOUND.withPlaceholder("subcommand", args[1]));

            return cmd.getSubCommand(args[1]).executePlayer(null, command, args);

            // for (String subCommandName : cmd.getSubCommands().keySet()) {
            //     if (!(args[1].equalsIgnoreCase(subCommandName))) continue;
                
            //     return cmd.getSubCommands().get(subCommandName).executePlayer(null, command, args);
            // }
        }
        return new CommandResult(CommandOutcome.ERROR);
    }

    protected CommandResult executeConsole(org.bukkit.command.Command command, String[] args) {
        if (args.length == 0) {
            return new CommandResult(CommandOutcome.ERROR_NO_COMMAND);
        }

        if (commands.get(args[0]) == null) return new CommandResult(CommandOutcome.ERROR_NO_COMMAND_FOUND.withPlaceholder("command", args[0]));
        
        for (String commandName : commands.keySet()) { // ! TODO Untested
            if (!(args[0].equalsIgnoreCase(commandName))) continue;

            AbstractCommand<M> cmd = commands.get(commandName);

            if (cmd.isDirectCommand()) return cmd.executeConsole(command, args);

            if (args.length == 1) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND);

            if (!cmd.hasSubCommand(args[1])) return new CommandResult(CommandOutcome.ERROR_NO_SUBCOMMAND_FOUND.withPlaceholder("subcommand", args[1]));

            return cmd.getSubCommand(args[1]).executeConsole(command, args);
        }
        return new CommandResult(CommandOutcome.ERROR);
    }

    public List<AbstractCommand<M>> getCommands() {
        return new ArrayList<AbstractCommand<M>>(commands.values());
    }

    public abstract HashMap<String, AbstractCommand<M>> getCommandMap();

    public AbstractCommand<M> getCommand(String commandName) {
        return commands.get(commandName);
    }

    public void registerCommand(AbstractCommand<M> command) {
        commands.put(command.getCommandName(), command);
    }

    public boolean isSubCommand(AbstractCommand<M> command) {
        return command instanceof SubCommand;
    }

    public boolean isDirectCommand(AbstractCommand<M> command) {
        return !isSubCommand(command);
    }

    public CommandHandler<H, M> getCommandHandler() {
        return commandHandler;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}