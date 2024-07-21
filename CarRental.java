import java.util.*;


class Car{
    private String  carId;
    private String model;
    private String brand;
    private  double basePrice;
    private boolean isAvailable;
    
    public Car( String carId, String model,String brand, double basePrice){
         this.carId =carId;
         this.model= model;
         this.brand = brand;
         this.basePrice=basePrice;
         this.isAvailable=true;
    }

    public String getCarId(){
        return carId;
    }

    public String getModel(){
        return model;
    }

    public String getBrand(){
        return brand;
    }

    public double calculatePrice(int rentalDays){
        return basePrice * rentalDays;
    }

    public  boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable=false;
    }

    public void returnCar(){
        isAvailable=true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId,String name){
        this.customerId=customerId;
        this .name = name;
    }
    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name;
    }

}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getdays(){
        return days;
    }

}

class CarRentalSystem{
    private List<Car>cars;
    private List<Customer>customers;
    private List<Rental>rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }
    
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }else{
            System.out.println("Car is not available");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }

        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.print("Car returned successfully.");
        }else{
            System.out.print("Car not found");
        }
    }

    public void  menu(){

        Scanner s =new Scanner(System.in);
        while(true){
            System.out.println("\n\t ****Welcome to Car Rental Serives****");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter the suitable option:");
   
            int option = s.nextInt();
   
            if(option ==1){
   
                System.out.println("\t***Rent a Car***");
                System.out.println("Enter your name:");
                String customerName = s.next();
                System.out.println("\t--Available Cars--");
        
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+ " - "+car.getBrand()+" - "+car.getModel());
                    }
                }
        
                System.out.print("\nEnter the car ID:");
                String carId = s.next();
        
                System.out.print("\nEnter no.of days for rent:");
                int rentalDays = s.nextInt();
                s.nextLine();
                
                Customer newCustomer = new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                   } 
                }
   
                if(selectedCar != null){
                   double totalPrice=selectedCar.calculatePrice(rentalDays);
                   System.out.println("\t--Rental Information--");
                   System.out.println("Customer Id:"+newCustomer.getCustomerId());
                   System.out.println("Customer Name:"+newCustomer.getName());
                   System.out.println("Car :"+selectedCar.getBrand()+" "+selectedCar.getModel());
                   System.out.println("Rental Days:"+rentalDays);
                   System.out.println("Total Price: Rs"+totalPrice);
                   
                   System.out.println("\nConfirm rental(Y/N)");
                   String confirm = s.nextLine();
   
                   if(confirm.equalsIgnoreCase("Y")){
                       rentCar(selectedCar,newCustomer,rentalDays);
                       System.out.print("\n --Car has been rented Succesfully--\n");    
                   }else{
                       System.out.println("\nRental cancelled\n");
                   }
               }else{
                   System.out.println("\nCar not available or inavlid car \n");
               }
          
   
            }else if(option == 2){
                System.out.println("\n-- Return a car --\n");
                System.out.print("Enter the car ID:");
                String carId = s.next();
            
                Car carToReturn = null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null){
                    Customer customer =null;
                    for(Rental rental: rentals ){
                        if(rental.getCar()== carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }else {
                   System.out.println("Invalid car ID or car is not rented.");
                }
            }else if (option == 3) {
                break;
            }else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
       System.out.println("\nThank you for using the Car Rental System!");
    }  
} 


    public class CarRental{
        public static void main(String[] args) {
            CarRentalSystem rentalSystem = new CarRentalSystem();
            
            Car car1 = new Car("C101", "Hyundai", "Creta", 1500.0); // Different base price per day for each car
            Car car2 = new Car("C002", "Audi", "Q7", 5500.0);
            Car car3 = new Car("C003", "Mahindra", "Thar", 1000.0);
            Car car4 = new Car("C004", "Mini Cooper", "S", 4000.0);
            Car car5 = new Car("C005", "Kia", "Seltos", 1700.0);
            rentalSystem.addCar(car1);
            rentalSystem.addCar(car2);
            rentalSystem.addCar(car3);
            rentalSystem.addCar(car4);
            rentalSystem.addCar(car5);
            
            rentalSystem.menu();
        }
    }
                 
    
