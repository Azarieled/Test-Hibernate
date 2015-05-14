package tk.exarus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
import static java.lang.Integer.MAX_VALUE;
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

    private static DrugDealer dom;
    private static DrugDealer sub;

    private static DrugShop thisIsShop;
    private static DrugShop madness;

    private static DrugCorporation sparta;

    @BeforeClass
    public static void setUp() throws Exception {

        // init corporation
        sparta = new DrugCorporation("Sparta", dom, null);




        // init shops and add them to corporation
        thisIsShop = new DrugShop("Black Sabbath", null, sparta);
        madness = new DrugShop("Madness", null, sparta);
        sparta.setDrugShops(asList(thisIsShop, madness));

        // init dealers
        dom = new DrugDealer("Ozzy", "Osbourne", null,
                new Timestamp(-665193600), new Timestamp(-665193600),
                FALSE, Sex.M, ROCK, thisIsShop, MAX_VALUE);
        sub = new DrugDealer("Sergei", "Gonta", dom,
                new Timestamp(830476800), new Timestamp(830476800),
                TRUE, Sex.M, RAP, madness, -100500);

        // add dealers to shops
        thisIsShop.setDrugDealers(singletonList(dom));
        madness.setDrugDealers(singletonList(sub));

        // add boss
        sparta.setBoss(dom);
    }

    @Before
    public void addTestData() {
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

    @After
    public void deleteTestData() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.delete(sparta);
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
            logger.info("");
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

    @Test
    public void testGetNativeSQLCorporation() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("SELECT * FROM drug_corporation LIMIT 1")
                    .addEntity(DrugCorporation.class);
            DrugCorporation corporation = (DrugCorporation) query.list().get(0);

            // logging result
            logger.info("");
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

    @Test
    public void testGetHQLCorporation() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();

            String hql = "from tk.exarus.entity.DrugShop as shop " +
                    "left join fetch shop.drugCorporation as drugCorporation " +
                    "left join fetch shop.drugDealers as drugDealers " +
                    "where shop.name = 'Black Sabbath'";
            Query query = session.createQuery(hql);
            List<DrugShop> shops = (List<DrugShop>) query.list();
            DrugShop shop = shops.get(0);

            logger.info("");
            logger.info(shops.size());

            // logging result
            logger.info(shop.getDrugCorporation().getName());
            logger.info(shop);
            shop.getDrugDealers().forEach(logger::info);

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
