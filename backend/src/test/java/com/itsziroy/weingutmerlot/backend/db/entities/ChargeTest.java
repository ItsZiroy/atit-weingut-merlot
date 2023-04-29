package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChargeTest {

    public static Charge createRandomCharge(boolean persist) {

        Charge charge = new Charge();
        charge.setJahrgang((int) (Math.random() * 2000));
        charge.setLagerungsort("Test");
        charge.setMengeInLiter(Math.random() * 1000);
        Wein wein = WeinTest.createRandomWein(true);
        charge.setWein(wein);

        if(persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(charge);
            DB.getEntityManager().getTransaction().commit();
        }

        return charge;
    }
    public static Charge createRandomChargeForWein(boolean persist, Wein wein) {

        Charge charge = new Charge();
        charge.setJahrgang((int) (Math.random() * 2000));
        charge.setLagerungsort("Test");
        charge.setMengeInLiter(Math.random() * 1000);
        charge.setWein(wein);

        if(persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(charge);
            DB.getEntityManager().getTransaction().commit();
        }

        return charge;
    }

    @Test
    void chargePersistence() {
        Assertions.assertDoesNotThrow(() -> createRandomCharge(true));
    }
}
