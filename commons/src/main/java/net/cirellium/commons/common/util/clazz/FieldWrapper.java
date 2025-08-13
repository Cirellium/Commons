/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:13:55
*
* FieldWrapper.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.util.clazz;

import java.lang.reflect.Field;

@SuppressWarnings( {"unchecked", "deprecation"} )
public class FieldWrapper<T> {
    
    private Field field;

    private final ClassWrapper<?> owner;

    public FieldWrapper(ClassWrapper<?> owner, String name) {
      this.owner = owner;
      try {
        if (this.owner != null && this.owner.getClazz() != null)
          this.field = this.owner.getClazz().getDeclaredField(name); 
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } 
    }

    public T get() {
        try {
            if (this.field != null) {
                this.field.setAccessible(true);
                return (T) this.field.get(this.owner.get());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(T value) {
        if (this.field.isAccessible())
            this.field.setAccessible(true);
        try {
            this.field.set(this.owner.get(), value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}