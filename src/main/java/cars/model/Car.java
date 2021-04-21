package cars.model;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private boolean sold;

    @OneToOne
    @JoinColumn(name = "car_photo_id", foreignKey = @ForeignKey(name = "CAR_PHOTO_ID_FK"))
    private CarPhoto photo;

    @ManyToOne
    @JoinColumn(name = "seller_id", foreignKey = @ForeignKey(name = "SELLER_ID_FK"))
    private Seller seller;

    public static Car of(String name, String description, boolean sold) {
        Car car = new Car();
        car.name = name;
        car.description = description;
        car.sold = sold;
        return car;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public CarPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CarPhoto photo) {
        this.photo = photo;
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
        Car car = (Car) o;
        return this.id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sold=" + sold +
                ", seller=" + seller +
                '}';
    }
}