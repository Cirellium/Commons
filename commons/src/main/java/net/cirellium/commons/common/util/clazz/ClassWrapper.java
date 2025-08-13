/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:11:06
*
* ClassWrapper.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.util.clazz;

public class ClassWrapper<T> {
    private Class<T> clazz;

    private final T object;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ClassWrapper(T object) {
      if (object != null) this.clazz = (Class) object.getClass(); 
      this.object = object;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    public T get() {
        return this.object;
    }

    public MethodWrapper getMethod(String name, Object... parameters) {
        return new MethodWrapper(this, name, parameters);
    }

    public <ST> FieldWrapper<ST> getField(String name) {
        return new FieldWrapper<>(this, name);
    }
}