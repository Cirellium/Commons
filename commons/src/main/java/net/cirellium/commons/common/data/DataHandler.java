/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:01:13
*
* UserDataHolder.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DataHandler<L extends Loadable<?>, D extends Data> {

    protected final List<L> loadableData;
    protected final List<? extends D> registeredData;

    public DataHandler() {
        this.loadableData = new ArrayList<L>();
        this.registeredData = new ArrayList<>();
    }

    public abstract void load(L loadable);

    public abstract void loadAll();

}