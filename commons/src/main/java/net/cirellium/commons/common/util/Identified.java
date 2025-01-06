package net.cirellium.commons.common.util;

public interface Identified<I extends Comparable<I>> {
    
    I getId();
    
    void setId(I id);

}