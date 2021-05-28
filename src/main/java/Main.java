import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Main {

  private static String ADD_CAR = "Add a Car";
  private static String LIST_CARS = "List Cars";
  private static String SEARCH_CARS = "Search for a Car";
  private static String EXIT = "Exit";

  private static Scanner inputScanner;

  public static void main(String[] args) {
    List<String> options = new ArrayList<String>();
    options.add(ADD_CAR);
    options.add(LIST_CARS);
    options.add(SEARCH_CARS);
    options.add(EXIT);

    String menuChoice = "";
    while (menuChoice != EXIT) {
      System.out.println("CARS! CARS! CARS!");
      System.out.println("***");
      System.out.println("Coffee is for closers.\n");

      int menuIndex = 1;
      for (String option : options) {
        System.out.println(menuIndex + ". " + option);
        menuIndex++;
      }

      System.out.println("\nWhat would you like to do?");
      inputScanner = new Scanner(System.in);

      int selectedOption = inputScanner.nextInt();
      menuChoice = options.get(selectedOption - 1);
      if (menuChoice == ADD_CAR) {
        addCar();
      } else if (menuChoice == LIST_CARS) {
        listCars();
      } else if (menuChoice == SEARCH_CARS) {
        searchCars();
      }
    }
  }
  public static void searchCars(){
    System.out.println("Enter the VIN: ");
    String carVin = inputScanner.nextLine();
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("com.launchacademy.carDealership");
    EntityManager em = emf.createEntityManager();

    String vinCheck = "SELECT c FROM Car c WHERE vin = :vin";
    Query vinCheckQuery = em.createQuery(vinCheck, Car.class);
    vinCheckQuery.setParameter("make", carVin);

    List<Car> retreivedVin = vinCheckQuery.getResultList();

    em.getTransaction().begin();
    if (retreivedVin.size() > 0) {
      System.out.println("Press a to update price");
      System.out.println("Press b to delete entry");
      String options = inputScanner.nextLine();
      if(options.contains("a")){
        System.out.println("Enter a new price");
        String newPrice = inputScanner.nextLine();

      }
    } else {
      Make newMake = new Make();
      newMake.setMake(carMake);
      em.persist(newMake);
      newCar.setMake(newMake);
    }
  }
  public static void addCar() {
    Car newCar = new Car();
    inputScanner.nextLine();
    System.out.println("Enter the VIN: ");
    String carVin = inputScanner.nextLine();
    System.out.println("Enter the year: ");
    int carYear = inputScanner.nextInt();
    System.out.println("Enter the make: ");
    inputScanner.nextLine();
    String carMake = inputScanner.nextLine();
    System.out.println("Enter the asking price: ");
    int carPrice = inputScanner.nextInt();
    inputScanner.nextLine();
    System.out.println("Enter the model: ");
    String carModel = inputScanner.nextLine();


    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("com.launchacademy.carDealership");
    EntityManager em = emf.createEntityManager();

    String makeCheck = "SELECT m FROM Make m WHERE make = :make";
    Query makeCheckQuery = em.createQuery(makeCheck, Make.class);
    makeCheckQuery.setParameter("make", carMake);

    List<Make> retreivedMakes = makeCheckQuery.getResultList();

    em.getTransaction().begin();
    if (retreivedMakes.size() > 0) {
      newCar.setMake(retreivedMakes.get(0));
    } else {
      Make newMake = new Make();
      newMake.setMake(carMake);
      em.persist(newMake);
      newCar.setMake(newMake);
    }
    newCar.setVin(carVin);
    newCar.setYear(carYear);
    newCar.setAskingPrice(carPrice);
    newCar.setModel(carModel);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Car>> violations = validator.validate(newCar);
    if (violations.size() > 0) {
      for (ConstraintViolation<Car> violation : violations) {
        System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
      }
    } else {
      System.out.println("Car has been added to the database");
      em.persist(newCar);
      em.getTransaction().commit();
    }
    em.close();
    emf.close();
  }

  public static void listCars() {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("com.launchacademy.carDealership");
    EntityManager em = emf.createEntityManager();

    TypedQuery<Car> carListQuery = em
        .createQuery("SELECT c FROM Car c ORDER BY asking_price DESC", Car.class);

    List<Car> carList = carListQuery.getResultList();

    if (carList.size() > 0) {
      for (int i = 0; i < carList.size(); i++) {
        System.out.println(
            "VIN: " + carList.get(i).getVin() + "\n " +
                "Make: " + carList.get(i).getMake() + "\n " +
                "Model: " + carList.get(i).getModel() + "\n " +
                "Year: " + carList.get(i).getYear() + "\n " +
                "Asking Price: $" + carList.get(i).getAskingPrice() + "\n ");
      }
    }
    em.close();
    emf.close();
  }
}
