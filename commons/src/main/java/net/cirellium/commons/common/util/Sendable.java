package net.cirellium.commons.common.util;

@FunctionalInterface
public interface Sendable<CS> {
    void send(CS receiver);
}