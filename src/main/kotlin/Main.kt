import controllers.ClientAPI
import models.Appointment
import models.Client

import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput
import utils.ValidateInput.readValidDateofAppointment
import utils.ValidateInput.readValidPhone
import utils.ValidateInput.readValidReview
import utils.ValidateInput.readValidCategory
import java.io.File
import kotlin.system.exitProcess

/**
 * Main function to run the application.
 */
private val clientAPI = ClientAPI(XMLSerializer(File("clients.xml")))

fun main() = runMenu()

/**
 * Function to display and handle the main menu.
 */
fun runMenu() {
    do {
        val categoryChoice = readCategoryChoice()

        when (categoryChoice) {
            1 -> runClientMenu()
            2 -> runAppointmentMenu()
            3 -> runSearchingMenu()
            0 -> exitApp()
            else -> println("\u001B[35mInvalid menu choice: $categoryChoice\u001B[0m") // Pink color code
        }
    } while (true)
}

/**
 * Function to read the user's choice of category from the main menu.
 */
fun readCategoryChoice(): Int {
    println("\n-----------------------------------------------------")
    println("\u001B[35m             Select a category:\u001B[0m") // Pink color code
    println("-----------------------------------------------------")
    println("\u001B[94m1. Client Menu\u001B[0m") // Baby blue color code (94)
    println("\u001B[94m2. Appointment Menu\u001B[0m") // Baby blue color code (94)
    println("\u001B[94m3. Searching Menu\u001B[0m") // Baby blue color code (94)
    println("-----------------------------------------------------")
    println("\u001B[94m0. Exit\u001B[0m") // Baby blue color code (94)
    println("-----------------------------------------------------")
    print("\u001B[33mEnter your choice:\u001B[0m ")
    return readLine()?.toIntOrNull() ?: -1
}

/**
 * Function to handle the client menu options.
 */
fun runClientMenu() {
    do {
        val option = clientMenu()

        when (option) {
            in 1..6 -> processClientMenuOption(option)
            23 -> load()
            24 -> save()
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: ")
        }
    } while (true)
}

/**
 * Function to process the client menu option chosen by the user.
 */
fun processClientMenuOption(option: Int) {
    when (option) {
        1 -> addClient()
        2 -> listClients()
        3 -> updateClient()
        4 -> deleteClient()
        5 -> checkIfThereAreClients()
        6 -> clearAllClients()
        else -> println("Invalid menu choice: $option")
    }
}

/**
 * Function to display and handle the client menu.
 */
fun clientMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        >             Client-Appointment APP                  
        >-----------------------------------------------------
        > CLIENT MENU
        > 1) Add a client 
        > 2) List clients 
        > 3) Update a client 
        > 4) Delete a client 
        > 5) Check if there are clients stored 
        > 6) Clear all clients
        > -----------------------------------------------------
        > 23) Load clients
        > 24) Save clients 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

/**
 * Function to add a new client.
 */
fun addClient() {
    val firstName = ScannerInput.readNextLine("Enter the client's first name: ")
    val lastName = ScannerInput.readNextLine("Enter the client's last name: ")
    val address = ScannerInput.readNextLine("Enter the client's address: ")
    val email = ScannerInput.readNextLine("Enter the client's email: ")
    val phone = ValidateInput.readValidPhone("Enter the client's phone number: ")
    val extraInfo = ScannerInput.readNextLine("Enter the client's extra information: ")
    val isAdded = clientAPI.addClient(Client(firstName = firstName, lastName = lastName, address = address,  email = email, phone = phone, extraInfo = extraInfo))
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add has Failed")
    }
}

/**
 * Runs the appointment menu, allowing users to perform various operations related to appointments.
 */
fun runAppointmentMenu() {
    do {
        val option = appointmentMenu()

        when (option) {
            in 7..10 -> processAppointmentMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: ")
        }
    } while (true)
}

/**
 * Processes the selected option from the appointment menu.
 * @param option The selected option from the menu.
 */
fun processAppointmentMenuOption(option: Int) {
    when (option) {
        7 -> addAppointment()
        8 -> listScheduledAppointments()
        9 -> updateAppointment()
        10 -> deleteAppointment()
        else -> println("Invalid menu choice: $option")
    }
}

/**
 * Displays the appointment menu and prompts the user for input.
 * @return The user's menu choice.
 */
fun appointmentMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > APPOINTMENT MENU
        > ----------------------------------------------------
        > 7) Add an appointment 
        > 8) List confirmed appointments 
        > 9) Update an appointment 
        > 10) Delete an appointment 
        > -----------------------------------------------------  
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("\u001B[33mEnter your choice:\u001B[0m ")
    return readLine()?.toIntOrNull() ?: -1
}

/**
 * Adds an appointment for a client.
 */
fun addAppointment() {
    val isScheduled = readBooleanFromUserInputOfAppointmentConfirmationStatus()
    addAppointmentForClient(isScheduled)
}

/**
 * Updates an appointment for a client.
 */
fun updateAppointment() {
    val isScheduled = readBooleanFromUserInputOfAppointmentConfirmationStatus()
    val id = readNextInt("Enter the ID of the client: ")
    updateAppointmentForClient(isScheduled, id)
}

/**
 * Deletes an appointment for a client.
 */
fun deleteAppointment() {
    val id = readNextInt("Enter the ID of the client: ")
    deleteAnAppointmentForAClient(id)
}

/**
 * Runs the searching menu, allowing users to perform various search operations.
 */
fun runSearchingMenu() {
    do {
        val option = searchingMenu()

        when (option) {
            in 11..22 -> processSearchingMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice:")
        }
    } while (true)
}

/**
 * Displays the searching menu and prompts the user for input.
 * @return The user's menu choice.
 */
fun searchingMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > SEARCH MENU
        > ----------------------------------------------------
        > CLIENT SEARCH MENU 
        > ----------------------------------------------------
        > 11) Search for a client by their Id
        > 12) Search for a client by their first name 
        > 13) Search for a client by their last name 
        > 14) Search for a client by their address 
        > 15) Search for a client by their email 
        > 16) Search for a client by their phone number 
        > ----------------------------------------------------- 
        > APPOINTMENT SEARCH MENU
        > -----------------------------------------------------
        > 17) Search for an appointment by its Id 
        > 18) Search for an appointment by its time 
        > 19) Search for an appointment by its date 
        > 20) Search for an appointment by its category of treatment 
        > 21) Search for an appointment by its price 
        > 22) Search for an appointment by its review 
        > ----------------------------------------------------- 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("\u001B[33mEnter your choice:\u001B[0m ")
    return readLine()?.toIntOrNull() ?: -1
}

/**
 * Processes the selected option from the searching menu.
 * @param option The selected option from the menu.
 */
fun processSearchingMenuOption(option: Int) {
    when (option) {
        11 -> searchClientsById()
        12 -> searchClientsByFirstName()
        13 -> searchClientsByLastName()
        14 -> searchClientsByAddress()
        15 -> searchClientsByEmail()
        16 -> searchClientsByPhone()
        17 -> searchAppointmentsById()
        18 -> searchAppointmentsByTime()
        19 -> searchAppointmentsByDate()
        20 -> searchAppointmentsByCategory()
        21 -> searchAppointmentsByPrice()
        22 -> searchAppointmentsByReview()
        else -> println("Invalid menu choice: $option")
    }
}

/**
 * Adds an appointment for a client.
 * @param isScheduled Indicates whether the appointment is scheduled or not.
 */
fun addAppointmentForClient(isScheduled: Boolean) {
    val clientId = readNextInt("Enter the ID of the client: ")
    val client = clientAPI.searchClientById(clientId)
    if (client != null) {
        val appointment = createAppointmentFromUserInput(isScheduled)
        if (appointment != null) {
            val isAdded = client.addAppointment(appointment)
            if (isAdded) {
                println("Appointment added successfully.")
            } else {
                println("Failed to add appointment.")
            }
        } else {
            println("Invalid appointment details.")
        }
    } else {
        println("Client not found with ID: $clientId")
    }
}

/**
 * Creates an appointment from user input.
 * @param isScheduled Indicates whether the appointment is scheduled or not.
 * @return The created appointment.
 */
fun createAppointmentFromUserInput(isScheduled: Boolean): Appointment? {
    val time = ValidateInput.readValidTime("Enter the appointment time (in the form 09.00): ")
    val date = ValidateInput.readValidDateofAppointment("Enter the appointment date: ")
    val treatment = ValidateInput.readValidCategory("Enter the treatment for the appointment: ")
    val price = readNextInt("Enter the price for the appointment: ")
    val review = ValidateInput.readValidReview("Enter the review for the appointment: ")

    return Appointment(time = time, date = date, treatment = treatment, price = price, review = review, isScheduled = isScheduled)
}

/**
 * Lists clients and provides options to view all clients or the number of clients.
 */
fun listClients() {
    if (clientAPI.numberOfClients() > 0) {
        val option = ScannerInput.readNextInt(
            """
                  > --------------------------------
                  > 1) View ALL Clients 
                  > 2) List the NUMBER OF ALL Clients 
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllClients()
            2 -> listNumberOfAllClients()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No clients stored")
    }
}

/**
 * Lists all clients.
 */
fun listAllClients() = println(clientAPI.listAllClients())

/**
 * Lists the number of all clients.
 */
fun listNumberOfAllClients() = println(clientAPI.numberOfClients())

/**
 * Lists scheduled appointments.
 */
fun listScheduledAppointments(){
    if (clientAPI.listScheduledAppointments() > 0.toString()) {
        println("Total Scheduled Appointments: ${clientAPI.listScheduledAppointments()}")
    }
    println(clientAPI.listScheduledAppointments())
}

/**
 * Updates client information.
 */
fun updateClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        // Only ask the user to choose the client if clients exist
        val id = readNextInt("Enter the id of the client to update: ")
        if (clientAPI.searchClientById(id) != null) {
            val firstName = readNextLine("Enter the client's first name:  ")
            val lastName = readNextLine("Enter the client's last name:  ")
            val address = readNextLine("Enter the client's address:  ")
            val phone = readValidPhone("Enter the client's phone number:  ")
            val email = readNextLine("Enter the client's email: ") // Ensure email is obtained from user input
            val extraInfo = readNextLine("Enter the client's extraInfo:  ")

            // Pass the id and new client details to ClientAPI for updating and check for success.
            if (clientAPI.updateClient(id, Client(firstName = firstName, lastName = lastName, address = address, email = email, phone = phone, extraInfo = extraInfo))){
                println("Update was Successful")
            } else {
                println("Update has Failed")
            }
        } else {
            println("There are no clients available for this index number")
        }
    }
}

/**
 * Updates appointment information for a client.
 */
fun updateAppointmentForClient(isScheduled: Boolean, id: Int) {
    val client: Client? = clientAPI.searchClientById(id)
    if (client != null) {
        val appointment: Appointment? = askUserToChooseAppointment(client)
        if (appointment != null) {
            val newTime = ScannerInput.readNextDouble("Enter a new appointment time: ")
            val newDateOfA = readValidDateofAppointment("Enter a new appointment date: ")
            val newCategory = readValidCategory("Enter the services that the new appointment will provide: ")
            val newPrice = readNextInt("Enter a new appointment price: ")
            val isItScheduled = isScheduled
            val newReview = readValidReview("Enter a new review: ")
            if (client.updateAppointment(appointment.appointmentId, Appointment(time = newTime, date = newDateOfA,
                    treatment = newCategory, price = newPrice, isScheduled = isItScheduled, review = newReview))) {
                println("Appointment Details updated successfully")
            } else {
                println("Appointment Details not updated")
            }
        } else {
            println("Invalid Appointment Id")
        }
    }
}



/**
 * Deletes a client.
 */
fun deleteClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val id = readNextInt("Enter the id of the client to delete: ")
        val clientToDelete = clientAPI.deleteClient(id)
        if (clientToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

/**
 * Deletes an appointment for a client.
 */
fun deleteAnAppointmentForAClient(id: Int) {
    val client: Client? = clientAPI.searchClientById(id)
    if (client != null) {
        val appointment: Appointment? = askUserToChooseAppointment(client)
        if (appointment != null) {
            val isDeleted = client.deleteAppointment(appointment.appointmentId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

/**
 * Clears all clients from the system.
 */
fun clearAllClients() {
    clientAPI.clearAllClients()
    println("All clients have been cleared from the system")
}

/**
 * Searches clients by first name.
 */
fun searchClientsByFirstName() {
    val searchQuery = readNextLine("Enter the client's first name: ")
    val searchResults = clientAPI.searchClientByFirstName(searchQuery)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches clients by last name.
 */
fun searchClientsByLastName() {
    val searchQuery = readNextLine("Enter the client's last name: ")
    val searchResults = clientAPI.searchClientByLastName(searchQuery)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches clients by address.
 */
fun searchClientsByAddress() {
    val searchQuery = readNextLine("Enter the client's address: ")
    val searchResults = clientAPI.searchClientByAddress(searchQuery)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches clients by email.
 */
fun searchClientsByEmail() {
    val searchQuery = readNextLine("Enter the client's email: ")
    val searchResults = clientAPI.searchClientByEmail(searchQuery)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches clients by phone.
 */
fun searchClientsByPhone() {
    val searchQuery = readNextInt("Enter the client's phone number: ")
    val searchResults = clientAPI.searchClientByPhone(searchQuery)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches clients by ID.
 */
fun searchClientsById() {
    val clientId = readNextInt("Enter the client's ID: ")
    val client = clientAPI.searchClientById(clientId)
    if (client != null) {
        println("Client found: $client")
    } else {
        println("Client not found with ID: $clientId")
    }
}
/**
 * Searches appointments by ID.
 */
fun searchAppointmentsById() {
    val searchQuery = readNextInt("Enter the appointment's id: ")
    val searchResults = clientAPI.searchAppointmentById(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches appointments by time.
 */
fun searchAppointmentsByTime()
{
    val searchQuery = ScannerInput.readNextDouble("Enter the time: ")
    val searchResults = clientAPI.searchAppointmentByTime(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches appointments by date.
 */
fun searchAppointmentsByDate() {
    val searchQuery = readNextLine("Enter the date: ")
    val searchResults = clientAPI.searchAppointmentByDate(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches appointments by category.
 */
fun searchAppointmentsByCategory() {
    val searchQuery = readNextLine("Enter the category of treatment: ")
    val searchResults = clientAPI.searchAppointmentByCategories(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches appointments by price.
 */
fun searchAppointmentsByPrice() {
    val searchQuery = readNextInt("Enter the price: ")
    val searchResults = clientAPI.searchAppointmentByPrice(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches appointments by review.
 */
fun searchAppointmentsByReview() {
    val searchQuery = readNextInt("Enter the review: ")
    val searchResults = clientAPI.searchAppointmentByReview(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}


/**
 * Checks if there are clients.
 */
fun checkIfThereAreClients() {
    println(clientAPI.checkIfThereAreClients())
}

/**
 * Exits the application.
 */
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

/**
 * Reads boolean from user input of appointment confirmation status.
 */
fun readBooleanFromUserInputOfAppointmentConfirmationStatus(): Boolean {
    while (true) {
        val input = readNextLine("Enter yes or no to confirm whether the appointment is Scheduled or not: ")
        if (input.equals("yes", ignoreCase = true)) {
            return true
        } else if (input.equals("no", ignoreCase = true)) {
            return false
        } else {
            println("Invalid input. Please enter 'yes' or 'no'.")
        }
    }
}

/**
 * Asks the user to choose an appointment for a client.
 */
private fun askUserToChooseAppointment(Client: Client): Appointment? {
    if (Client.numberOfAppointments() > 0) {
        print(Client.listAppointments())
        return Client.findAppointmentById(readNextInt("\nEnter the id of the item: "))
    } else {
        println("No items for chosen note")
        return null
    }
}

/**
 * Saves client data.
 */
fun save() {
    try {
        clientAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Loads client data.
 */
fun load() {
    try {
        clientAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
