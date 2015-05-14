package tk.exarus.entity;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Ruslan Gunavardana
 */
@Entity
@Table(name = "drug_shop")
public class DrugShop {

    @Id
    private String name;

    @OneToMany(mappedBy = "drugShop", targetEntity = DrugDealer.class, cascade = ALL, fetch = LAZY)
    private List<DrugDealer> drugDealers;

    @ManyToOne
    private DrugCorporation drugCorporation;

    public DrugShop() {
    }

    public DrugShop(String name, List<DrugDealer> drugDealers, DrugCorporation drugCorporation) {
        this.name = name;
        this.drugDealers = drugDealers;
        this.drugCorporation = drugCorporation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DrugDealer> getDrugDealers() {
        return drugDealers;
    }

    public void setDrugDealers(List<DrugDealer> drugDealers) {
        this.drugDealers = drugDealers;
    }

    public DrugCorporation getDrugCorporation() {
        return drugCorporation;
    }

    public void setDrugCorporation(DrugCorporation drugCorporation) {
        this.drugCorporation = drugCorporation;
    }

    @Override
    public String toString() {
        return "DrugShop{" +
                "name='" + name + '\'' +
                ", drugCorporation=" + drugCorporation.getName() +
                '}';
    }
}
