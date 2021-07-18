package cars.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "advert")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text", nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean sold;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seller_id",
            foreignKey = @ForeignKey(name = "SELLER_ID_FK"), nullable = false)
    private Seller seller;

    public static Advert of(String name, int price, String description,
                            String address, Date created, Car car, Seller seller) {
        Advert advert = new Advert();
        advert.name = name;
        advert.price = price;
        advert.description = description;
        advert.address = address;
        advert.created = created;
        advert.car = car;
        advert.seller = seller;
        return advert;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advert advert = (Advert) o;
        return this.id == advert.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Advert{id=%d, %n name='%s', %n price=%s"
                        + "%n description='%s', %n address=%s, %n sold=%s, "
                        + "created=%s, %n car=%s, %n seller=%s}",
                id, name, price, description, address, sold, created, car, seller);
    }
}