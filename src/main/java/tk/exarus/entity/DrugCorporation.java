package tk.exarus.entity;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Ruslan Gunavardana
 */
@Entity
@Table(name = "drug_corporation")
public class DrugCorporation {
    private String name;
    private DrugDealer boss;
    private List<DrugShop> drugShops;

    public DrugCorporation() {
    }

    public DrugCorporation(String name, DrugDealer boss, List<DrugShop> drugShops) {
        this.name = name;
        this.boss = boss;
        this.drugShops = drugShops;
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(targetEntity = DrugDealer.class)
    public DrugDealer getBoss() {
        return boss;
    }

    public void setBoss(DrugDealer boss) {
        this.boss = boss;
    }

    @OneToMany(mappedBy = "drugCorporation", targetEntity = DrugShop.class, fetch = LAZY, cascade = ALL)
    public List<DrugShop> getDrugShops() {
        return drugShops;
    }

    public void setDrugShops(List<DrugShop> drugShops) {
        this.drugShops = drugShops;
    }

    @Override
    public String toString() {
        return "DrugCorporation{" +
                "name='" + name + '\'' +
                ", boss=" + boss +
                '}';
    }
}
