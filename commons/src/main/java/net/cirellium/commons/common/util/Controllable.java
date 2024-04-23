package net.cirellium.commons.common.util;

public interface Controllable<T> {

    // @SuppressWarnings("unchecked")
    // default T getController() {
    //     final ParameterizedType interfaceClass = (ParameterizedType) Arrays
    //             .stream(this.getClass().getGenericInterfaces())
    //             .filter(type -> type.getTypeName().contains(Controllable.class.getSimpleName()))
    //             .findFirst().orElse(null);

    //     if (interfaceClass != null) {
    //         final Class<T> genericTypeClass = (Class<T>) interfaceClass.getActualTypeArguments()[0];

    //         return HCTeams.getInstance().getHandler().findController(genericTypeClass);
    //     }

    //     throw new IllegalStateException("No interface by name Controllable found.");
    // }

}