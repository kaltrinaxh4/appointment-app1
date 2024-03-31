import controllers.ClientAPI
import models.Appointment
import models.Client
//import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
//import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput
import utils.ValidateInput.readValidDateofAppointment

import utils.ValidateInput.readValidPhone
import utils.ValidateInput.readValidReview
import utils.ValidateInput.readValidCategory
import java.io.File
import kotlin.system.exitProcess


private val clientAPI = ClientAPI(XMLSerializer(File("notes.xml")))

fun main() = runMenu()

fun runMenu() {
    do {
        val categoryChoice = readCategoryChoice()

        when (categoryChoice) {
            1 -> runClientMenu()
            2 -> runAppointmentMenu()
            3 -> runSearchingMenu()
            0 -> exitApp()
            else -> println("Invalid menu choice: $categoryChoice")
        }
    } while (true)
}

fun readCategoryChoice(): Int {
    println("Select a category:")
    println("1. Client Menu")
    println("2. Appointment Menu")
    println("3. Searching Menu")
    println("0. Exit")
    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

fun runClientMenu() {
    do {
        val option = clientMenu()

        when (option) {
            in 1..6 -> processClientMenuOption(option)
            23 -> load()
            24 -> save()
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

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
        > 5) Check if there are clients stored in the system
        > 6) Clear all clients from the data system 
       1
        > -----------------------------------------------------
        > 23) Load clients
        > 24) Save clients 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

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

fun runAppointmentMenu() {
    do {
        val option = appointmentMenu()

        when (option) {
            in 7..10 -> processAppointmentMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun processAppointmentMenuOption(option: Int) {
    when (option) {
        7 -> addAppointment()
        8 -> listConfirmedAppointments()
        9 -> updateAppointment()
        10 -> deleteAppointment()
        else -> println("Invalid menu choice: $option")
    }
}

fun appointmentMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > APPOINTMENT MENU
        > 7) Add an appointment 
        > 8) List confirmed appointments 
        > 9) Update an appointment 
        > 10) Delete an appointment 
        > -----------------------------------------------------  
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

fun addAppointment() {
    val isScheduled = readBooleanFromUserInputOfAppointmentConfirmationStatus()
    addAppointmentForClient(isScheduled)
}

fun listConfirmedAppointments() {
    listScheduledAppointments()
}

fun updateAppointment() {
    val isScheduled = readBooleanFromUserInputOfAppointmentConfirmationStatus()
    val id = readNextInt("Enter the ID of the client: ")
    updateAppointmentForClient(isScheduled, id)
}

fun deleteAppointment() {
    val id = readNextInt("Enter the ID of the client: ")
    deleteAnAppointmentForAClient(id)
}

fun runSearchingMenu() {
    do {
        val option = searchingMenu()

        when (option) {
            in 11..16 -> processSearchingMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun searchingMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > CLIENT SEARCH MENU 
        > 11) Search for a client by their Id
        > 12) Search for a client by their first name 
        > 13) Search for a client by their last name 
        > 14) Search for a client by their address 
        > 15) Search for a client by their email 
        > 16) Search for a client by their phone number 
        > ----------------------------------------------------- 
        > APPOINTMENT SEARCH MENU
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

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

fun processSearchingMenuOption(option: Int) {
    when (option) {
        in 11..16 -> processClientMenuOption(option) // Reusing processClientMenuOption for client search options
        in 17..22 -> processAppointmentMenuOption(option) // Reusing processAppointmentMenuOption for appointment search options
        else -> println("Invalid menu choice: $option")
    }
}

fun addAppointmentForClient(isScheduled: Boolean) {
    val clientId = readNextInt("Enter the ID of the client: ")
    val client = clientAPI.findClientById(clientId)
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

fun createAppointmentFromUserInput(isScheduled: Boolean): Appointment? {
    val time = ValidateInput.readValidTime("Enter the appointment time (in the form 09.00): ")
    val date = ValidateInput.readValidDateofAppointment("Enter the appointment date: ")
    val treatment = ValidateInput.readValidCategory("Enter the treatment for the appointment: ")
    val price = readNextInt("Enter the price for the appointment: ")
    val review = ValidateInput.readValidReview("Enter the review for the appointment: ")

    return Appointment(time = time, date = date, treatment = treatment, price = price, review = review, isScheduled = isScheduled)
}

fun listClients() {
    if (clientAPI.numberOfClients() > 0) {
        val option = ScannerInput.readNextInt(
            """
                  > --------------------------------
                  > 1) View ALL Clients 
                  > 2) List the NUMBER OF ALL Clients 
              
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllClients()
            2 -> listNumberOfAllClients()

            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No clients stored")
    }
}
fun listAllClients() = println(clientAPI.listAllClients())
fun listNumberOfAllClients() = println(clientAPI.numberOfClients())

fun listScheduledAppointments(){
    if (clientAPI.listScheduledAppointments() > 0.toString()) {
        println("Total Confirmed Appointments: ${clientAPI.listScheduledAppointments()}")
    }
    println(clientAPI.listScheduledAppointments())
}

fun updateClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        // Only ask the user to choose the client if clients exist
        val id = readNextInt("Enter the id of the client to update: ")
        if (clientAPI.findClientById(id) != null) {
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

fun updateAppointmentForClient(isScheduled: Boolean, id: Int) {
    val client: Client? = clientAPI.findClientById(id)
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


fun deleteClient()
{
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
fun deleteAnAppointmentForAClient(id: Int) {
    val client: Client? = clientAPI.findClientById(id)
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

fun clearAllClients()
{
    clientAPI.clearAllClients()
    println( " All clients have been cleared from the system")
}


fun listClientsbyId()
{
    val searchResults = readNextInt("Enter the client's Id: ")
    println(clientAPI.findClientById(searchResults))
}


fun searchClientsByFirstName()
{
    val searchQuery = readNextLine("Enter the client's first name: ")
    val searchResults = clientAPI.searchClientByFirstName(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No clients found")
    } else {
        println(searchResults)
    }
}


fun searchClientsByLastName()
{
    val searchQuery = readNextLine("Enter the client's last name: ")
    val searchResults = clientAPI.searchClientByLastName(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No clients found")
    } else {
        println(searchResults)
    }
}


fun searchClientsByAddress()
{
    val searchQuery = readNextLine("Enter the client's address: ")
    val searchResults = clientAPI.searchClientByAddress(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No clients found")
    } else {
        println(searchResults)
    }
}




fun searchClientsByEmail()
{
    val searchQuery = readNextLine("Enter the client's email: ")
    val searchResults = clientAPI.searchClientByEmail(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No clients found")
    } else {
        println(searchResults)
    }
}



fun searchClientsByPhone()
{
    val searchQuery = readNextInt("Enter the client's phone number: ")
    val searchResults = clientAPI.searchClientByPhone(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No clients found")
    } else {
        println(searchResults)
    }
}



fun searchAppointmentsById()
{
    val searchQuery = readNextInt("Enter the appointment's id: ")
    val searchResults = clientAPI.searchAppointmentById(searchQuery)
    if (searchResults.isEmpty())
    {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}


fun searchAppointmentsByTime()
{
    val searchQuery = ScannerInput.readNextDouble("Enter the time to search by: ")
    val searchResults = clientAPI.searchAppointmentByTime(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}


fun searchAppointmentsByDate()
{
    val searchQuery = readNextLine("Enter the date to search by: ")
    val searchResults = clientAPI.searchAppointmentByDate(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}



fun searchAppointmentsByCategory()
{
    val searchQuery = readNextLine("Enter the treatment to search by: ")
    val searchResults = clientAPI.searchAppointmentByCategories(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

fun searchAppointmentsByPrice()
{
    val searchQuery = readNextInt("Enter the price to search by: ")
    val searchResults = clientAPI.searchAppointmentByPrice(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}


fun searchAppointmentsByReview()
{
    val searchQuery = readNextInt("Enter the review to search by: ")
    val searchResults = clientAPI.searchAppointmentByReview(searchQuery)
    if (searchResults.isEmpty()) {
        println("No appointments found")
    } else {
        println(searchResults)
    }
}

fun checkIfThereAreClients() = println(clientAPI.checkIfThereAreClients())

fun exitApp()
{
    println("Exiting...bye")
    exitProcess(0)
}

fun readBooleanFromUserInputOfAppointmentConfirmationStatus(): Boolean
{
    while (true)
    {
        val input = readNextLine("Enter true or false to indicate whether the appointment is Scheduled or not: ")
        if (input.equals("true", ignoreCase = true)) {
            return true
        } else if (input.equals("false", ignoreCase = true)) {
            return false
        } else
        {
            println("Invalid input. Please enter 'true' or 'false'.")
        }
    }
}
private fun askUserToChooseAppointment(Client: Client): Appointment?
{
    if (Client.numberOfAppointments() > 0) {
        print(Client.listAppointments())
        return Client.findAppointmentById(readNextInt("\nEnter the id of the item: "))
    } else {
        println("No items for chosen note")
        return null
    }
}
fun save()
{
    try {
        clientAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}
fun load()
{
    try {
        clientAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}