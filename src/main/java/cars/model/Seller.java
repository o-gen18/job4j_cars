package cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;

    public static Seller of(String name, String email, String password) {
        Seller seller = new Seller();
        seller.name = name;
        seller.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
