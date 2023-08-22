package net.cirellium.commons.common.util;

@FunctionalInterface
public interface ClassTyped {

    /**
     * Returns the class type of this object
     * 
     * @return This object's class type
     */
    Class<?> getType();

}