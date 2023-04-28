package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.HefeService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class HefeManager implements HefeService {

    @Override
    public List<Hefe> getAll() {
        return DB.getEntityManager().createQuery("select h from Hefe h", Hefe.class).getResultList();
    }

    @Override
    public Hefe getOne(int id) {
        return DB.getEntityManager().find(Hefe.class, id);
    }
}
