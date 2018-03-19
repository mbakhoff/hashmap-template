import java.util.ArrayList;
import java.util.List;

/**
 * oop2016ut collections vol2
 * @author Taavi Ilp
 */
public class Company {

  private String name;
  private List<String> customerNames = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addCustomer(String name) {
    customerNames.add(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Company company = (Company) o;

    return name.equals(company.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
