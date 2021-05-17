package cars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Advert> ads = new ArrayList<>();

    public void addAd(Advert advert) {
        this.ads.add(advert);
        advert.setSeller(this);
    }

    public void removeAd(Advert advert) {
        this.ads.remove(advert);
        advert.setSeller(null);
    }

    public static Seller of(String name, String email, String phone, String password) {
        Seller seller = new Seller();
        seller.name = name;
        seller.email = email;
        seller.phone = phone;
        seller.password = password;
        return seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Advert> getAds() {
        return ads;
    }

    public void setAds(List<Advert> ads) {
        this.ads = ads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seller seller = (Seller) o;
        return this.id == seller.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String[] adsNames = ads.stream().map(Advert::getName).toArray(String[]::new);
        return String.format(
                "Seller{id=%d, %n name='%s', "
                        + "%n email='%s', %n phone=%s, %n password='%s', %n ads=%s}",
                id, name, email, phone, password, Arrays.toString(adsNames));
    }
}