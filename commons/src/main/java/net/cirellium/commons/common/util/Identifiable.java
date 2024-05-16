package net.cirellium.commons.common.util;

public interface Identifiable<I extends Comparable<I>> {
    
    I getId();
    
    void setId(I id);

}