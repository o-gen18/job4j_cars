package cars.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "car_photo")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private byte[] photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarPhoto carPhoto = (CarPhoto) o;
        return this.id == carPhoto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarPhoto{" +
                "id=" + id +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
