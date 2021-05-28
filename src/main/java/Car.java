import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "cars")
public class Car {

  @Id
  @SequenceGenerator(name = "cars_generator", sequenceName = "cars_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @NotEmpty
  @Length(min = 5, max = 20)
  @Column(name = "vin")
  private String vin;

  @NotNull
  @Range(min = 1981, max = 2029)
  @Column(name = "year")
  private int year;

  @ManyToOne
  @JoinColumn(name = "make_id")
  private Make make;

  @NotNull
  @Range(min = 500, max = 50000)
  @Column(name = "asking_price")
  private int askingPrice;

  @NotEmpty
  @Column(name = "model")
  private String model;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Make getMake() {
    return make;
  }

  public void setMake(Make make) {
    this.make = make;
  }


  public int getAskingPrice() {
    return askingPrice;
  }

  public void setAskingPrice(int askingPrice) {
    this.askingPrice = askingPrice;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
}
