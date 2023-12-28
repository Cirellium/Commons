package net.cirellium.commons.bukkit.command.annotation.argument.provided;

import java.util.List;
import java.util.Set;

public class ProvidedArguments {
    
    private final Set<String> flags;

    private final List<String> arguments;

    public ProvidedArguments(List<String> arguments, Set<String> flags) {
      this.arguments = arguments;
      this.flags = flags;
    }

    // public static ProvidedArguments of(String commandLine) {
    //     return of(Arrays.asList(commandLine.split(" ")));
    // }

    public static ProvidedArguments of(List<String> arguments) {
        return new ProvidedArgumentProcessor().process(arguments);
    }

    public boolean hasFlag(String flag) {
        return this.flags.contains(flag.toLowerCase());
    }

    public String join(int from, int to, char delimiter) {
        if (to > this.arguments.size() - 1 || to < 1)
            to = this.arguments.size() - 1;
        StringBuilder builder = new StringBuilder();
        for (int i = from; i <= to; i++) {
            builder.append(this.arguments.get(i));
            if (i != to)
                builder.append(delimiter);
        }
        return builder.toString();
    }

    public String join(int from, char delimiter) {
        return join(from, -1, delimiter);
    }

    public String join(int from) {
        return join(from, ' ');
    }

    public String join(char delimiter) {
        return join(0, delimiter);
    }

    public String join() {
        return join(' ');
    }

    public Set<String> getFlags() {
        return this.flags;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return "ProvidedArguments [flags=" + this.flags + ", arguments=" + this.arguments + "]";
    }
}