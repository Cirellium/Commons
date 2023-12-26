package net.cirellium.commons.bukkit.prompt.response;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.util.ClassTyped;

public enum ResponseType implements ClassTyped {

    STRING(String.class, new ResponseTypeHandler<String>() {
        @Override
        public String parse(final String input) {
            return input;
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(String.class, type);
        }
    }),

    INTEGER(Integer.class, new ResponseTypeHandler<Integer>() {
        @Override
        public Integer parse(final String input) {
            return Integer.parseInt(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Integer.class, type);
        }
    }),

    DOUBLE(Double.class, new ResponseTypeHandler<Double>() {
        @Override
        public Double parse(final String input) {
            return Double.parseDouble(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Double.class, type);
        }
    }),

    BOOLEAN(Boolean.class, new ResponseTypeHandler<Boolean>() {
        @Override
        public Boolean parse(final String input) {
            return Boolean.parseBoolean(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Boolean.class, type);
        }
    }),

    WORLD(World.class, new ResponseTypeHandler<World>() {
        @Override
        public World parse(final String input) {
            return Bukkit.getWorld(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(World.class, type);
        }
    }),

    PLAYER(Player.class, new ResponseTypeHandler<Player>() {
        @Override
        public Player parse(final String input) {
            return Bukkit.getPlayer(input);
        }

        @Override
        public boolean supports(final Class<?> type) {
            return assignable(Player.class, type);
        }
    });

    private final Class<?> type;
    private final ResponseTypeHandler<?> handler;

    ResponseType(final Class<?> type, ResponseTypeHandler<?> handler) {
        this.type = type;
        this.handler = handler;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    public ResponseTypeHandler<?> getHandler() {
        return handler;
    }

    public boolean supports(final Class<?> type) {
        return type.isAssignableFrom(type);
    }

    static boolean assignable(final Class<?> clazz, final Class<?> type) {
        return clazz.isAssignableFrom(type);
    }
}