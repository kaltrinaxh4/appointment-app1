
import controllers.ClientAPI
import models.Appointment
import models.Client

import persistence.XMLSerializer

import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput.readValidDate
import utils.ValidateInput.readValidEmail
import utils.ValidateInput.readValidPhone
import utils.ValidateInput.readValidRating
import utils.ValidateInput.readValidTime
import utils.ValidateInput.readValidTreatment
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