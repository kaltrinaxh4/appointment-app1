import controllers.ClientAPI
import models.Client
import utils.ScannerInput
import utils.ScannerInput.readNextInt

private val clientAPI = ClientAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt("""
         > -------------------------------------
         > |       Appointment App          |
         > -------------------------------------
         > | MAIN MENU                                |
         > |   1) Client Management                   |
         > |   2) Exit                                   |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> runClientMenu()
            2 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun exitApp() {
    TODO("Not yet implemented")
}

fun runClientMenu() {
    do {
        val option = clientMenu()
        when (option) {
            1 -> addClient()
            2 -> listClients()
            3 -> updateClient()
            4 -> deleteClient()
            5 -> archiveClient()
            6 -> searchClients()
            7 -> runAppointmentMenu()
            20 -> saveClients()
            21 -> loadClients()
            0 -> break
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun loadClients() {
    TODO("Not yet implemented")
}

fun saveClients() {
    TODO("Not yet implemented")
}

fun clientMenu(): Int {
    return ScannerInput.readNextInt("""
         > --------------------------------------
         > |       Client Management         |
         > -------------------------------------
         > | CLIENT MENU                            |
         > |   1) Add a Client                        |
         > |   2) List Clients                        |
         > |   3) Update a Client                     |
         > |   4) Delete a Client                     |
         > |   5) Archive a Client                    |
         > |   6) Search Clients (by name)            |
         > |   7) Manage Appointments           |
         > -------------------------------------
         > |   20) Save Clients                       |
         > |   21) Load Clients                       |
         > |   0) Back to Main Menu              |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
}

fun addClient() {
    val firstName = ScannerInput.readNextLine("Enter the client's first name: ")
    val lastName = ScannerInput.readNextLine("Enter the client's last name: ")
    val phoneNumber = ScannerInput.readNextLine("Enter the client's phone number: ")
    val town = ScannerInput.readNextLine("Enter the client's town: ")
    val street = ScannerInput.readNextLine("Enter the client's street: ")
    val city = ScannerInput.readNextLine("Enter the client's city: ")

    val isAdded = clientAPI.add(Client(0, firstName, lastName, phoneNumber, Client.Address(town, street, city)))

    if (isAdded) {
        println("Client Added Successfully")
    } else {
        println("Client Add Failed")
    }
}

fun listClients() {
    println(clientAPI.listAllClients())
}

fun updateClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val clientId = readNextInt("Enter the ID of the client to update: ")
        if (clientAPI.isValidIndex(clientId)) {
            val updatedFirstName = ScannerInput.readNextLine("Enter the updated first name: ")
            val updatedLastName = ScannerInput.readNextLine("Enter the updated last name: ")
            val updatedPhoneNumber = ScannerInput.readNextLine("Enter the updated phone number: ")
            val updatedTown = ScannerInput.readNextLine("Enter the updated town: ")
            val updatedStreet = ScannerInput.readNextLine("Enter the updated street: ")
            val updatedCity = ScannerInput.readNextLine("Enter the updated city: ")

            if (clientAPI.update(clientId, Client(0, updatedFirstName, updatedLastName, updatedPhoneNumber, Client.Address(updatedTown, updatedStreet, updatedCity)))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There is no client with this ID")
        }
    }
}

fun deleteClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val clientId = readNextInt("Enter the ID of the client to delete: ")
        val deletedClient = clientAPI.delete(clientId)
        if (deletedClient) {
            println("Delete Successful! Deleted Client ID: $clientId")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val clientId = readNextInt("Enter the ID of the client to archive: ")
        if (clientAPI.archiveClient(clientId)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun searchClients() {
    val searchString = ScannerInput.readNextLine("Enter the name to search for: ")
    val searchResults = clientAPI.searchClientsByName(searchString)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

fun runAppointmentMenu() {
    do {
        val option = appointmentMenu()
    }
