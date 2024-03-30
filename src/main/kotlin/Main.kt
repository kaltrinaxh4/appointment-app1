
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