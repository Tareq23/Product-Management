<h2>Product Management API with Domain-Driven Design (DDD)</h2>

This is a RESTful Product Management API built with Spring Boot, structured using Domain-Driven Design (DDD) principles. The application encapsulates the domain model, manages business rules in the domain layer, bridges the domain and infrastructure with application services, and abstracts data storage through repositories.

---

## Key Features

1. **Domain Model**: Defines `Product` as the core entity with supporting value objects: `Category`, `Discount`, `Price`, and `StockQuantity`.
2. **Business Rules in Domain Layer**: Business logic is contained within the domain layer to ensure data integrity.
3. **Application Services**: Acts as an intermediary between the domain and infrastructure.
4. **Repository Abstraction**: Handles data storage and retrieval with a repository pattern.

---

## Project Structure

### 1. Domain Model (`Product`)

The `Product` entity encapsulates the core product details and associates value objects to enforce consistency.

```java
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Embedded
    private Price price;

    @Embedded
    private StockQuantity stockQuantity;

    @Embedded
    private Category category;

    @Embedded
    private Discount discount;
}
```
#### Value Objects
Each value object has its own rules to enforce validity:
```java
@Embeddable
public class Price {
    @Column(name = "price")
    private BigDecimal price;

    public Price(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price should not be negative.");
        }
        this.price = price;
    }
}
```
Here, the `Price` value object enforces non-negative values, ensuring the price remains valid.

### 2. Business Rules in the Domain Layer
Business rules for the `Product` entity are encapsulated within value objects and the entity itself. This design keeps the business logic centralized and reusable.
```java
public class StockQuantity {
    private Integer quantity;

    public StockQuantity(Integer quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Stock quantity must be non-negative.");
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity - amount < 0) throw new IllegalArgumentException("Insufficient stock quantity.");
        this.quantity -= amount;
    }
}
```
In this example, `StockQuantity` ensures that quantity values are kept non-negative and provides methods for safe addition and subtraction.

### 3. Application Services
   The application layer acts as a bridge between the domain and infrastructure layers. It processes client requests, coordinates domain operations, and interacts with repositories.
```java
@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements ProductDomainService {

    private final ProductJpaRepository repository;

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = new Product(
            productDto.getName(),
            new Category(productDto.getCategory()),
            productDto.getDescription(),
            new Price(productDto.getPrice()),
            new StockQuantity(productDto.getStockQuantity()),
            new Discount(productDto.getPercentage())
        );
        repository.save(product);
    }
}
```
This example illustrates the `createProduct` method, which constructs a `Product` using value objects and persists it via the repository.

### 4. Repository Abstraction
Repositories abstract data storage and retrieval, adhering to DDD by separating persistence from business logic.
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
```

### API Endpoints
The following endpoints are provided for managing products:

* POST  :    /products - Create a new product
* GET   :    /products - Retrieve paginated list of products
* GET   :    /products/{id} - Retrieve a product by ID
* PUT   :    /products/{id} - Update a product
* PATCH :    /products/{id}/update-stock - Update stock quantity
* DELETE:    /products/{id} - Delete a product



## Get Started

To set up and run this application on a different computer, follow these steps:

### 1. Clone the Project
- Clone this repository to your local machine:
  ```bash
  git clone <https://github.com/Tareq23/Product-Management.git>
  ```
- Or download and transfer the project files.

### 2. Install Java
- Ensure Java 17 (or the required Java version) is installed on your machine. Verify with:
  ```bash
  java -version
  ```

### 3. Open the project in `Intellij`
- Click on run icon button :

### 4. Configure Application
- The application uses an H2 in-memory database and will run on port 8090, as configured in `application.yaml`:
- The H2 database console is accessible at `http://localhost:8090/h2-db`.

### 5. Access the Application
- The application will be accessible at `http://localhost:8090`.
- Test above API endpoints with tools like [Postman](https://www.postman.com/).

Now you’re all set up to explore and use the Product Management API on your local machine!



