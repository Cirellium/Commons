package net.cirellium.commons.bukkit.prompt.response;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.util.ClassTyped;

public enum ResponseType implements ClassTyped {

    STRING(String.class, new ResponseTypeAdapter<String>() {
        @Override
        public String parse(PromptResponder responder, final String input) {
            return input;
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(String.class, type);
        }
    }),

    INTEGER(Integer.class, new ResponseTypeAdapter<Integer>() {
        @Override
        public Integer parse(PromptResponder responder, final String input) {
            return Integer.parseInt(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Integer.class, type);
        }
    }),

    DOUBLE(Double.class, new ResponseTypeAdapter<Double>() {
        @Override
        public Double parse(PromptResponder responder, final String input) {
            return Double.parseDouble(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Double.class, type);
        }
    }),

    BOOLEAN(Boolean.class, new ResponseTypeAdapter<Boolean>() {
        @Override
        public Boolean parse(PromptResponder responder, final String input) {
            return Boolean.parseBoolean(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Boolean.class, type);
        }
    }),

    WORLD(World.class, new ResponseTypeAdapter<World>() {
        @Override
        public World parse(PromptResponder responder, final String input) {
            return Bukkit.getWorld(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(World.class, type);
        }
    }),

    PLAYER(Player.class, new ResponseTypeAdapter<Player>() {
        @Override
        public Player parse(PromptResponder responder, final String input) {
            return Bukkit.getPlayer(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Player.class, type);
        }
    });

    private final Class<?> type;
    private final ResponseTypeAdapter<?> handler;

    ResponseType(final Class<?> type, ResponseTypeAdapter<?> handler) {
        this.type = type;
        this.handler = handler;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    public ResponseTypeAdapter<?> getHandler() {
        return handler;
    }

    public boolean supports(final Class<?> type) {
        return type.isAssignableFrom(type);
    }

    static boolean assignable(final Class<?> clazz, final Class<?> type) {
        return clazz.isAssignableFrom(type);
    }
}