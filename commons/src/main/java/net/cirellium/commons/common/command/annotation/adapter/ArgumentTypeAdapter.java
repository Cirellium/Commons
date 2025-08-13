/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:20:02
*
* ArgumentTypeHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.annotation.adapter;

/** 
 * A class that handles the parsing and tab completion of arguments for a command.
 * 
 * It can suggest a list of possible results for the argument, parse the argument, and tab complete the argument.
 * Also, it can check if it supports a certain class.
 * 
 * @param <T> The type of the argument
 */
public interface ArgumentTypeAdapter<T, S> {

    T parse(S sender, String argument);

    boolean supports(Class<?> clazz);
    
}