package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class HefeManager {
    public static List<Hefe> getAllHefen(){
        TypedQuery<Hefe> query = DB.getEntityManager().createQuery("select h from Hefe h", Hefe.class);
        List<Hefe> hefen = query.getResultList();
        return hefen;
    }
}
