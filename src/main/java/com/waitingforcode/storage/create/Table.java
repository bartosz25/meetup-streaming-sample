package com.waitingforcode.storage.create;

import java.util.Collection;

public abstract class Table {

    public abstract String createQuery();

    public abstract Collection<String> createIndexQueries();

}
