package com.itsziroy.weingutmerlot.backend;

import java.util.List;

public interface Service<T> {
    List<T> getAll();
    T getOne(int id);
}
