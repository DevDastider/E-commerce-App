# E-Commerce-App

This project is the backend for the [e-commerce-ui](https://github.com/DevDastider/e-commerce-ui) project. It provides all the necessary functions for an e-commerce application.

## Table of Contents

- Introduction
- Features
- Technologies used
- Prerequisites
- Installation
- API Endpoints
- Contributing
- Author

## Introduction

This is an E-commerce application developed using Spring Boot. It provides various functionalities required for an online store, including user authentication, product management, and order processing.

## Features

- **User Registration and Login:** Secure user authentication and management.
- **Product Management:** Add, update, and delete products.
- **Cart Management:** Add and manage products in the cart.
- **Order Management:** Place orders and track order status.
- **Payment Integration:** Integrated with Razorpay API for handling payments.
- **User Profiles:** Separate profiles for users and admins.

## Technologies Used

- **Java:** Core programming language.
- **Spring Boot:** Framework for building the backend application.
- **MySQL:** Database for storing application data.
- **Razorpay API:** Payment gateway integration.

## Prerequisites

- Java 17
- Maven
- MySQL
- An IDE such as IntelliJ IDEA or Eclipse

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/DevDastider/E-commerce-App.git
   cd E-commerce-App
   ```

2. **Configure the database:**
   Update the `application.properties` file with your MySQL database credentials.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

## User Endpoints

### Register New User

- **URL:** `/registerNewUser`
- **Method:** `POST`
- **Description:** Registers a new user.
- **Request Body:** `User` object

### Register New Admin

- **URL:** `/registerNewAdmin`
- **Method:** `POST`
- **Description:** Registers a new admin.
- **Request Body:** `User` object

### For Admin

- **URL:** `/forAdmin`
- **Method:** `GET`
- **Description:** Endpoint accessible only by admin users.
- **Response:** `"This is for ADMIN"`

### For User

- **URL:** `/forUser`
- **Method:** `GET`
- **Description:** Endpoint accessible only by regular users.
- **Response:** `"This is for USER"`

## Product Endpoints

### Add New Product

- **URL:** `/addProduct`
- **Method:** `POST`
- **Description:** Adds a new product.
- **Request Parts:** `Product` object and `MultipartFile[]` for images

### Get All Products

- **URL:** `/getAllProducts`
- **Method:** `GET`
- **Description:** Retrieves a list of all products.
- **Query Parameters:** `pageNumber` (default: 0), `searchKey` (default: "")

### Delete Product Details

- **URL:** `/deleteProductDetails/{productId}`
- **Method:** `DELETE`
- **Description:** Deletes a product by its ID.

### Get Product Details By ID

- **URL:** `/getProductDetailsById/{productId}`
- **Method:** `GET`
- **Description:** Retrieves product details by its ID.

### Get Product Details

- **URL:** `/getProductDetails/{isSingleProductCheckout}/{productId}`
- **Method:** `GET`
- **Description:** Retrieves product details for single product checkout or cart checkout.
- **Path Variables:** `isSingleProductCheckout`, `productId`

## Order Endpoints

### Place Order

- **URL:** `/placeOrder/{isCartCheckout}`
- **Method:** `POST`
- **Description:** Places an order.
- **Path Variables:** `isCartCheckout`
- **Request Body:** `OrderInput` object

### Get Order Details

- **URL:** `/getOrderDetails`
- **Method:** `GET`
- **Description:** Retrieves order details for the current user.

### Get All Order Details

- **URL:** `/getAllOrderDetails/{status}`
- **Method:** `GET`
- **Description:** Retrieves all order details based on status.
- **Path Variables:** `status`

### Mark Order Delivered

- **URL:** `/orderDelivered/{orderId}`
- **Method:** `GET`
- **Description:** Marks an order as delivered.
- **Path Variables:** `orderId`

### Create Transaction

- **URL:** `/createTransaction/{amount}`
- **Method:** `GET`
- **Description:** Creates a payment transaction.
- **Path Variables:** `amount`

## Cart Endpoints

### Add to Cart

- **URL:** `/addToCart/{productId}`
- **Method:** `GET`
- **Description:** Adds a product to the cart.
- **Path Variables:** `productId`

### Get Cart Details

- **URL:** `/getCartDetails`
- **Method:** `GET`
- **Description:** Retrieves cart details for the current user.

### Delete Cart Item

- **URL:** `/deleteCartItem/{cartId}`
- **Method:** `DELETE`
- **Description:** Deletes an item from the cart.
- **Path Variables:** `cartId`

## Authentication Endpoints

### Authenticate

- **URL:** `/authenticate`
- **Method:** `POST`
- **Description:** Authenticates a user and returns a JWT token.
- **Request Body:** `LoginRequest` object

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Author

- **Sayantan Ghosh Dastider** - [DevDastider](https://github.com/DevDastider)
