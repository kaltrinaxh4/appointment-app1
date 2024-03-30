
import controllers.ClientAPI
import models.Appointment
import models.Client

import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput
import utils.ValidateInput.readValidCategory
import utils.ValidateInput.readValidDateofAppointment
import utils.ValidateInput.readValidPhone
import utils.ValidateInput.readValidReview


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
        > 23) Load clients
        > 24) Save clients 
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
        > 13) Add an appointment 
        > 14) List confirmed appointments 
        > 15) Update an appointment 
        > 16) Delete an appointment 
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
        > 10) Search for a client by their address 
        > 11) Search for a client by their email 
        > 12) Search for a client by their phone number 
        > ----------------------------------------------------- 
        > APPOINTMENT SEARCH MENU
        > 17) Search for an appointment by its Id 
        > 18) Search for an appointment by its time 
        > 19) Search for an appointment by its date 
        > 20) Search for an appointment by its treatments 
        > 21) Search for an appointment by its cost 
        > 22) Search for an appointment by its rating 
        > ----------------------------------------------------- 
        > 0) Back to main menu
        > ----------------------------------------------------- 
        > ==>> """.trimMargin(">"))

    print("Enter your choice: ")
    return readLine()?.toIntOrNull() ?: -1
}
fun processAppointmentMenuOption(option: Int) {
    when (option) {
        15 -> {
            val isConfirmedUserInput = readBooleanFromUserInputOfAppointmentConfirmationStatus()
            addAppointmentForClient(isConfirmedUserInput)
        }
        16 -> listConfirmedAppointments()
        17 -> {
            val isConfirmedUserInput = readBooleanFromUserInputOfAppointmentConfirmationStatus()
            updateAppointmentForClient(isConfirmedUserInput)
        }
        18 -> deleteAnAppointmentForAClient()

        else -> println("Invalid menu choice: $option")
    }
}
fun processSearchingMenuOption(option: Int) {
    when (option) {
        in 7..14 -> processClientMenuOption(option) // Reusing processClientMenuOption for client search options
        in 19..24 -> processAppointmentMenuOption(option) // Reusing processAppointmentMenuOption for appointment search options
        else -> println("Invalid menu choice: $option")
    }
}

fun addAppointmentForClient(isScheduled: Boolean) {
    val client: Client
        if (client.addAppointment(Appointment(
                time = ValidateInput.readValidTime("\t Appointment Time, in the form 09.00: "),
                date = readValidDateofAppointment("\t Appointment Date: "),
                treatment = readValidCategory("\t Appointment Services: "),
               price = ScannerInput.readNextInt("\t Appointment Cost:"),
                isScheduled = isScheduled,
                review = readValidReview("\t Appointment review:")
            )))
            println("Add is Complete!")
        else println("Add is Not Complete")
    }
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
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (clientAPI.findClientById(id) != null) {
            val firstName = readNextLine("Enter the client's first name:  ")
            val lastName = readNextLine("Enter the client's last name:  ")
            val address = readNextLine("Enter the client's address:  ")
            val phone = readValidPhone("Enter the client's phone number:  ")


            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (clientAPI.updateClient(id, Client(0, firstName, lastName, street, county, email, phone, allergy))){
                println("Update was Successful")
            } else {
                println("Update has Failed")
            }
        } else {
            println("There are no clients available for this index number")
        }
    }
}
fun updateAppointmentForClient(isScheduled: Boolean){
    val client: Client? = clientAPI.findClientById(id) // Assuming clientAPI is accessible
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
fun deleteAnAppointmentForAClient() {
    val client: Client? = clientAPI.findClientById(id) // Assuming clientAPI is accessible
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

