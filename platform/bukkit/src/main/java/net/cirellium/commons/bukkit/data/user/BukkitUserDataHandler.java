package net.cirellium.commons.bukkit.data.user;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.data.user.UserDataHandler;
import net.cirellium.commons.common.exception.InitializationException;
import net.cirellium.commons.common.exception.ShutdownException;
import net.cirellium.commons.common.util.Lifecycle;

public abstract class BukkitUserDataHandler extends UserDataHandler<AbstractBukkitUser> implements Lifecycle.Controller<CirelliumBukkitPlugin> {

    public BukkitUserDataHandler() {
    }

    @Override
    public abstract void initialize(CirelliumBukkitPlugin plugin) throws InitializationException;

    @Override
    public abstract void shutdown(CirelliumBukkitPlugin plugin) throws ShutdownException;

}