package tk.exarus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import tk.exarus.entity.DrugCorporation;
import tk.exarus.entity.DrugDealer;
import tk.exarus.entity.DrugShop;
import tk.exarus.enumaration.Sex;
import tk.exarus.util.HibernateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static tk.exarus.enumaration.Music.RAP;
import static tk.exarus.enumaration.Music.ROCK;
import static tk.exarus.util.HibernateUtils.getSession;

/**
 * @author Ruslan Gunavardana
 */
public class TestHibernate {
    private static final Logger logger = LogManager.getLogger(TestHibernate.class);

    private DrugDealer dom;
    private DrugDealer sub;

    private DrugShop thisIsShop;
    private DrugShop madness;

    private DrugCorporation sparta;

    @Before
    public void setUp() throws Exception {

        // init corporation
        sparta = new DrugCorporation("Sparta", dom, null);




        // init shops and add them to corporation
        thisIsShop = new DrugShop("Black Sabbath", null, sparta);
        madness = new DrugShop("Madness", null, sparta);
        sparta.setDrugShops(asList(thisIsShop, madness));

        // init dealers
        dom = new DrugDealer("Ozzy", "Osbourne", null,
                new Timestamp(-665193600), new Timestamp(-665193600),
                FALSE, Sex.M, ROCK, thisIsShop);
        sub = new DrugDealer("Sergei", "Gonta", dom,
                new Timestamp(830476800), new Timestamp(830476800),
                TRUE, Sex.M, RAP, madness);

        // add dealers to shops
        thisIsShop.setDrugDealers(singletonList(dom));
        madness.setDrugDealers(singletonList(sub));

        // add boss
        sparta.setBoss(dom);
    }

    @Test
    public void testAddCorporation() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.save(sparta);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test
    public void testUpdateCorporation() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            sub.setDrugShop(thisIsShop);
            session.update(sparta);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test(expected = HibernateException.class)
    public void testUpdateNotNull() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            dom.setDrugAddicted(null);
            session.update(sparta);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test
    public void testGetCorporation() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            DrugCorporation corporation = (DrugCorporation) session.get(DrugCorporation.class, "Sparta");

            // logging result
            logger.info(corporation);
            for (DrugShop drugShop : corporation.getDrugShops()) {
                logger.info(corporation);
                drugShop.getDrugDealers().forEach(logger::info);
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
