# Understanding HTTP Methods in a RESTful API

## Introduction
RESTful APIs rely on HTTP methods to perform different operations on resources. The most commonly used HTTP methods are:

- **GET**: Retrieve data from the server
- **POST**: Create new resources
- **PUT**: Update existing resources
- **DELETE**: Remove resources

Each method has a specific use case and serves a unique purpose in managing data over the web.

## HTTP Methods and Their Purpose

### 1. **GET**
- **Purpose**: Used to retrieve data from the server.
- **Usage**: Should not modify any resources; only fetch data.
- **Example**:
  ```http
  GET /users
  ```
  Retrieves a list of all users.

  ```http
  GET /users/1
  ```
  Retrieves details of the user with ID 1.

### 2. **POST**
- **Purpose**: Used to create a new resource on the server.
- **Usage**: The request body contains the data for the new resource.
- **Example**:
  ```http
  POST /users
  Content-Type: application/json

  {
    "name": "John Doe",
    "email": "john@example.com"
  }
  ```
  Creates a new user with the provided data.

### 3. **PUT**
- **Purpose**: Used to update an existing resource.
- **Usage**: The request body contains the updated data.
- **Example**:
  ```http
  PUT /users/1
  Content-Type: application/json

  {
    "name": "John Doe",
    "email": "john.new@example.com"
  }
  ```
  Updates the user with ID 1.

### 4. **DELETE**
- **Purpose**: Used to remove a resource from the server.
- **Usage**: No request body is needed.
- **Example**:
  ```http
  DELETE /users/1
  ```
  Deletes the user with ID 1.

## Query Parameters vs. Path Variables

### Query Parameters
- Used for filtering, sorting, and searching.
- Typically optional and provide additional details about a request.
- Example:
  ```http
  GET /products?category=electronics&sort=price
  ```
  Retrieves products filtered by category "electronics" and sorted by price.

### Path Variables
- Used to identify specific resources.
- Example:
  ```http
  GET /products/123
  ```
  Retrieves details of the product with ID 123.

### When to Use Query Parameters vs. Path Variables

| Feature             | Query Parameters                        | Path Variables           |
|---------------------|---------------------------------|---------------------------|
| Use case           | Filtering, sorting, pagination | Identifying a specific resource |
| Example           | `/products?category=shoes`       | `/products/5`             |
| Optionality       | Often optional                  | Required                  |

## When to Use the Request Body

The request body is used when sending structured data, such as:
- Creating a new resource (POST)
- Updating an existing resource (PUT)

### Advantages of Using the Request Body:
1. **Supports complex data**: JSON payloads allow sending multiple fields in one request.
2. **More secure than query parameters**: Sensitive data (like passwords) should not be sent in URLs.
3. **Improved readability**: Keeps URLs clean and structured.

### Example of a Request Body:
```http
POST /orders
Content-Type: application/json

{
  "customer_id": 101,
  "items": [
    {"product_id": 1, "quantity": 2},
    {"product_id": 5, "quantity": 1}
  ]
}
```

## Conclusion
Understanding HTTP methods and when to use query parameters, path variables, and the request body is essential for designing efficient RESTful APIs. Proper use ensures better performance, security, and maintainability of APIs.
