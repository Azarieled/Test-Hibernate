package tk.exarus.entity;

import tk.exarus.enumaration.Music;
import tk.exarus.enumaration.Sex;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

/**
 * @author Ruslan Gunavardana
 */
@Entity
@Table(name = "drug_dealer")
public class DrugDealer {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @ManyToOne(targetEntity = DrugDealer.class)
    private DrugDealer boss;

    private Timestamp dob;

    @Basic
    @Column(name = "job_start_date", nullable = false)
    private Timestamp jobStartDate;

    @Column(name="drug_addicted", nullable = false)
    private Boolean drugAddicted;

    @Enumerated(STRING)
    private Sex sex;

    @Enumerated(STRING)
    @Column(name = "favorite_music")
    private Music favoriteMusic;

    @ManyToOne
    private DrugShop drugShop;

    public DrugDealer() {
    }

    public DrugDealer(String name, String surname,
                      DrugDealer boss, Timestamp dob,
                      Timestamp jobStartDate, Boolean drugAddicted,
                      Sex sex, Music favoriteMusic, DrugShop drugShop) {
        this.name = name;
        this.surname = surname;
        this.boss = boss;
        this.dob = dob;
        this.jobStartDate = jobStartDate;
        this.drugAddicted = drugAddicted;
        this.sex = sex;
        this.favoriteMusic = favoriteMusic;
        this.drugShop = drugShop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DrugDealer getBoss() {
        return boss;
    }

    public void setBoss(DrugDealer boss) {
        this.boss = boss;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public Timestamp getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(Timestamp jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public Boolean getDrugAddicted() {
        return drugAddicted;
    }

    public void setDrugAddicted(Boolean drugAddicted) {
        this.drugAddicted = drugAddicted;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Music getFavoriteMusic() {
        return favoriteMusic;
    }

    public void setFavoriteMusic(Music favoriteMusic) {
        this.favoriteMusic = favoriteMusic;
    }

    public DrugShop getDrugShop() {
        return drugShop;
    }

    public void setDrugShop(DrugShop drugShop) {
        this.drugShop = drugShop;
    }

    @Override
    public String toString() {
        return "DrugDealer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", boss=" + boss +
                ", dob=" + dob +
                ", jobStartDate=" + jobStartDate +
                ", drugAddicted=" + drugAddicted +
                ", sex=" + sex +
                ", favoriteMusic=" + favoriteMusic +
                ", drugShop=" + drugShop.getName() +
                '}';
    }
}
