import controllers.AppointmentAPI
import models.Appointment
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.CategoryUtility
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput.readValidCategory
import utils.ValidateInput.readValidPriority
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
//private val AppointmentAPI = AppointmentAPI(XMLSerializer(File("Appointments.xml")))
private val appointmentAPI = AppointmentAPI(JSONSerializer(File("appointments.json")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > -------------------------------------
         > |          Appointment APP          |
         > -------------------------------------
         > | APPOINTMENT MENU                         |
         > |   1) Add an Appointment                   |
         > |   2) List Appointments                   |
         > |   3) Update an Appointment                |
         > |   4) Delete an Appointment                |
         > |   5) Cancel an Appointment               |
         > |   6) Search Appointment(by description)  |
         > -------------------------------------
         > |   20) Save Appointments                  |
         > |   21) Load Appointments                  |
         > -------------------------------------
         > |   0) Exit                         |
         > -------------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addAppointment()
            2  -> listAppointments()
            3  -> updateAppointment()
            4  -> deleteAppointment()
            5 -> archiveAppointment()
            6 -> searchAppointment()
            20  -> save()
            21  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addAppointment(){
    //logger.info { "addAppointment() function invoked" }
    val appointmentsID = readNextLine("Enter an ID for the Appointment: ")
    val appointmentPriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val appointmentCategory = readValidCategory("Enter a category for the Appointment from ${CategoryUtility.categories}: ")
    val isAdded = appointmentAPI.add(Appointment(appointmentTitle, appointmentPriority, appointmentCategory, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listAppointments(){
    if (appointmentAPI.numberOfAppointments() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Appointments          |
                  > |   2) View ACTIVE sAppointment       |
                  > |   3) View CANCELLED Appointments     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllAppointments();
            2 -> listActiveAppointments();
            3 -> listCancelledAppointments();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No Appointments stored");
    }
}

fun listAllAppointments() {
    println(appointmentAPI.listAllAppointments())
}

fun listActiveAppointment() {
    println(appointmentAPI.listActiveAppointments())
}

fun listCancelledAppointments() {
    println(appointmentAPI.listCancelledAppointments())
}

fun updateAppointment() {
    //logger.info { "updateAppointments() function invoked" }
    listAppointments()
    if (appointmentAPI.numberOfAppointments() > 0) {
        //only ask the user to choose the Appointment if Appointments exist
        val indexToUpdate = readNextInt("Enter the index of the appointment to update: ")
        if (appointmentAPI.isValidIndex(indexToUpdate)) {
            val appointmentID = readNextLine("Enter a ID for the appointment: ")
            val appointmentPriority = readValidPriority("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val appointmentCategory = readValidCategory("Enter a category for the appointment from ${CategoryUtility.categories}: ")

            //pass the index of the Appointment and the new Appointment details to AppointmentAPI for updating and check for success.
            if (appointmentAPI.updateAppointment(indexToUpdate, Appointment(appointmentID, appointmentPriority, appointmentCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no Appointments for this index number")
        }
    }
}

fun deleteAppointment(){
    //logger.info { "deleteAppointment() function invoked" }
    listAppointments()
    if (appointmentAPI.numberOfAppointments() > 0) {
        //only ask the user to choose the Appointment to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the Appointment to delete: ")
        //pass the index of the note to AppointmentAPI for deleting and check for success.
        val noteToDelete = appointmentAPI.deleteAppointment(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted Appointment: ${appointmentToDelete.appointmentID}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun CancelAppointment() {
    listActiveAppointments()
    if (appointmentAPI.numberOfActiveAppointments() > 0) {
        //only ask the user to choose the Appointment to cancel if active Appointments exist
        val indexToCancel = readNextInt("Enter the index of the Appointment to cancel: ")
        //pass the index of the note to AppointmentAPI for cancelling and check for success.
        if (appointmentAPI.cancelAppointment(indexToCancel)) {
            println("Cancel Successful!")
        } else {
            println("Cancel NOT Successful")
        }
    }
}

fun searchAppointments() {
    val searchID = readNextLine("Enter the description to search by: ")
    val searchResults = appointmentAPI.searchByID(searchID)
    if (searchResults.isEmpty()) {
        println("No Appointments found")
    } else {
        println(searchResults)
    }
}
fun save() {
    try {
        appointmentAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        appointmentAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    logger.info { "exitApp() function invoked" }
    exit(0)
}
