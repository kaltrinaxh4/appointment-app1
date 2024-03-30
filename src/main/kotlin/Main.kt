import controllers.ClientAPI
import models.Appointment
import models.Client
import persistence.XMLSerializer
import java.io.File

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