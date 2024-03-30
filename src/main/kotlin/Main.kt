
import controllers.ClientAPI
import models.Appointment
import models.Client

import persistence.XMLSerializer
import utils.ScannerInput
import utils.ValidateInput


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
            25 -> load()
            26 -> save()
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}
fun processClientMenuOption(option: Int) {
    when (option) {
        1 -> {
            val hasPaidUserInput = readBooleanFromUserInputOfClientPaymentStatus()
            addClient(hasPaidUserInput)
        }
        2 -> listClients()
        3 -> {
            val hasPaidUserInput = readBooleanFromUserInputOfClientPaymentStatus()
            updateClient(hasPaidUserInput)
        }
        4 -> deleteClient()
        5 -> clearAllClients()
        6 -> checkIfThereAreClients()

    }
}
fun clientMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        >             NOTE KEEPER APP                  
        >-----------------------------------------------------
        > CLIENT MENU
        > 1) Add a client 
        > 2) List clients 
        > 3) Update a client 
        > 4) Delete a client 
        > 5) Clear all clients from the system 
        > 6) Check if there are clients in the system 
        > -----------------------------------------------------
        > 25) Load clients
        > 26) Save clients 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}

fun addClient(hasPaid: Boolean) {
    val firstName = ScannerInput.readNextLine("Enter the client's first name: ")
    val lastName = ScannerInput.readNextLine("Enter the client's last name: ")
    val address = ScannerInput.readNextLine("Enter the client's address: ")
    val email = ScannerInput.readNextLine("Enter the client's email: ")
    val phone = ValidateInput.readValidPhone("Enter the client's phone number: ")
    val extraInfo = ScannerInput.readNextLine("Enter the client's allergies: ")
    val isAdded = clientAPI.addClient(Client(firstName = firstName, lastName = lastName, street = street, county = county, email = email, phone = phone, allergy = allergy, hasPaid = hasPaid))
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun runAppointmentMenu() {
    do {
        val option = appointmentMenu()

        when (option) {
            in 15..18 -> processAppointmentMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}
fun runSearchingMenu() {
    do {
        val option = searchingMenu()

        when (option) {
            in 7..14, in 19..24 -> processSearchingMenuOption(option)
            0 -> return  // Return to the main menu
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}
fun appointmentMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > APPOINTMENT MENU
        > 15) Add an appointment 
        > 16) List confirmed appointments 
        > 17) Update an appointment 
        > 18) Delete an appointment 
        > -----------------------------------------------------  
     
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}
fun searchingMenu(): Int {
    println(""" 
        >-----------------------------------------------------
        > CLIENT SEARCH MENU 
        > 7) Search for a client by their Id
        > 8) Search for a client by their first name 
        > 9) Search for a client by their last name 
        > 10) Search for a client by their street 
        > 11) Search for a client by their county 
        > 12) Search for a client by their email 
        > 13) Search for a client by their phone number 
        > 14) Search for a client by their allergy 
        > ----------------------------------------------------- 
        > APPOINTMENT SEARCH MENU
        > 19) Search for an appointment by its Id 
        > 20) Search for an appointment by its time 
        > 21) Search for an appointment by its date 
        > 22) Search for an appointment by its treatments 
        > 23) Search for an appointment by its cost 
        > 24) Search for an appointment by its rating 
        > ----------------------------------------------------- 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}