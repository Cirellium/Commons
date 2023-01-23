/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:14:57
*
* MethodWrapper.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.util.clazz;

import java.lang.reflect.Method;

public class MethodWrapper {
    private final ClassWrapper<?> owner;

    private Method method;

    private final Object[] parameters;

    public MethodWrapper(ClassWrapper<?> owner, String name, Object... parameters) {
      this.owner = owner;
      this.parameters = parameters;
      Class<?>[] classes = new Class[parameters.length];
      for (int i = 0; i < parameters.length; i++)
        classes[i] = parameters[i].getClass(); 
      try {
        this.method = owner.getClazz().getDeclaredMethod(name, classes);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } 
    }

    public Object invoke() {
        this.method.setAccessible(true);
        if (this.method.getReturnType().equals(void.class)) {
            try {
                this.method.invoke(this.owner.get(), this.parameters);
            } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
        try {
            return this.method.getReturnType().cast(this.method.invoke(this.owner.get(), this.parameters));
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}