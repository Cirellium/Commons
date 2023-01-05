package net.cirellium.commons.bukkit.command;

public interface SubCommand {

    public AbstractCommand<?, ?> getMainCommand();

}
