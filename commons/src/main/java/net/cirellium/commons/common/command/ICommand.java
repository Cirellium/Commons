package net.cirellium.commons.common.command;

import java.util.List;

public interface ICommand {

    String getPermission();

    List<String> getAliases();
}