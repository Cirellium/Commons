package net.cirellium.commons.common.command.sender;

public interface CommandInvoker {

    void sendMessage(String message);

    boolean hasPermission(String permission);

    boolean isPlayer();
}