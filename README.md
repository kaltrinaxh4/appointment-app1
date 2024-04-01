# Client-Appointment Management System

## Overview

The Appointment Management System is a software application designed to streamline the process of managing client appointments for a salon or similar business. It provides functionalities for adding, updating, and deleting client information and appointments. This system helps salon owners or managers keep track of client appointments, services, and staff schedules efficiently.

## Why Models and Client API

In this project, we utilize models to represent the fundamental entities involved in the appointment management system, such as clients and appointments. The Client API acts as an interface to interact with these models, providing methods for performing various operations like adding, updating, and deleting clients and appointments.

By using models and the Client API, we ensure a modular and organized approach to managing data and business logic. This separation of concerns makes the system easier to maintain, extend, and test.

## Features

### 1. Client Management
   - Add, update, and delete client information.
   - Search for clients by ID, name, or other attributes.
   - View a list of all clients or scheduled appointments.

### 2. Appointment Management
   - Schedule appointments for clients, specifying details like date, time, service, and staff.
   - Search for appointments by date, category, price, review rating, or time.

### 3. Persistence
   - Save and load client data to/from XML or JSON files.
   - Ensure data persistence across application sessions.

### 4. Utilities
   - Validate appointment dates and categories using utility functions.

## Testing

Comprehensive unit tests are implemented to ensure the reliability and correctness of the system. These tests cover various functionalities, including client management, appointment scheduling, and data persistence.


## Implementation Details

Additionally, the KDoc plugin is integrated into the project to generate documentation directly from the code comments. This documentation serves as a valuable resource for developers to understand the system's architecture, classes, and methods.

One notable feature of the system is the implementation of a menu system, allowing users to navigate through different functionalities seamlessly. The menu system is hierarchical, with submenus for specific actions within each main menu option. This design enhances user experience and makes the system more intuitive to use.

## Contributing

Contributions to the Appointment Management System are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## Author

Kaltrina Xhokli
