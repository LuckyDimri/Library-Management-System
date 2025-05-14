Overview of the Project 	

The Smart Library Management System (SLMS) is a Java-based application developed to digitalize and automate traditional library processes. It is designed to support multiple roles—Admin, Librarian, and Student—with each role having specific permissions and access. The system handles various core functions such as book inventory management, issuing/returning books, fine calculation, and reporting. By replacing paper-based or semi-digital systems, SLMS significantly reduces human error, increases operational speed, and improves the overall efficiency of library management.

OOP Concepts Used:

Object-Oriented Programming (OOP) is a key principle used in designing the Smart Library Management System. The system utilizes the four pillars of OOP—Encapsulation, Inheritance, Polymorphism, and Abstraction—to build a modular, reusable, and maintainable codebase.

1. Class and Object
The project is structured using classes to represent real-world entities like:

•
Book

•
User

•
Librarian

•
Student

•
Transaction

Each class encapsulates data (fields) and behaviors (methods) that reflect their real-world functionality. For example, the Book class contains fields such as title, author, and availabilityStatus, and methods like issueBook() and returnBook().

Objects are instances of these classes, dynamically created during runtime to handle operations like issuing a book to a student or updating book details.

2. Inheritance
Inheritance allows code reuse by creating a hierarchy of classes. In the system:

•
A general User class is created as a parent (superclass).

•
Student and Librarian classes are child (subclasses) that inherit from User.
This allows shared attributes (e.g., userID, name, email) and behaviors (e.g., login(), logout()) to be defined in the User class, and specialized functionalities (e.g., issueBook() for librarian, viewIssuedBooks() for student) to be defined in subclasses.

4. Encapsulation
Encapsulation is achieved by:

•
Declaring fields (variables) as private.

•
Providing access through public getters and setters.

5. Polymorphism
Polymorphism is used to allow objects to behave differently based on their type. It is implemented through method overriding.

Example:

•
The login() method may be defined in the User class.

•
It is overridden in Librarian and Student classes to validate credentials based on role-specific logic.
This allows a common interface with customized implementations depending on the user role.

6. Abstraction
Abstraction hides implementation details and shows only essential features. In this system:

•
Abstract classes or interfaces are used to define common operations such as manageBooks() or generateReports().
