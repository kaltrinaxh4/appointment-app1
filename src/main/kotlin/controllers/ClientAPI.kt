package controllers

import models.Client
import persistence.Serializer
import utils.Utilities
import java.util.ArrayList

/**
 * Controller class for managing clients and their appointments.
 * @param serializerType The type of serializer to be used for data persistence.
 */
class ClientAPI(serializerType: Serializer) {

    private var clients = ArrayList<Client>()
    private var serializer: Serializer = serializerType
    private var lastClientId = 0

    /////////////////////////functions for client

    /**
     * Adds a client to the list of clients.
     * @param client The client to add.
     * @return true if the client is successfully added, false otherwise.
     */
    fun addClient(client: Client): Boolean {
        client.clientId = getClientId()
        return clients.add(client)
    }

    private fun getClientId() = lastClientId++

    /**
     * Lists all stored clients.
     * @return A formatted string containing the list of clients or "No clients stored" if the list is empty.
     */
    fun listAllClients() =
        if (clients.isEmpty()) {
            "No clients stored"
        } else {
            Utilities.formatListString(clients)
        }

    /**
     * Returns the number of clients stored.
     * @return The number of clients stored.
     */
    fun numberOfClients() = clients.size

    /**
     * Updates the information of a client.
     * @param id The ID of the client to update.
     * @param client The updated client information.
     * @return true if the client is successfully updated, false otherwise.
     */
    fun updateClient(id: Int, client: Client?): Boolean {
        val foundClient = searchClientById(id)
        if ((foundClient != null) && (client != null)) {
            foundClient.firstName = client.firstName
            foundClient.lastName = client.lastName
            foundClient.address = client.address
            foundClient.email = client.email
            foundClient.phone = client.phone
            foundClient.extraInfo = client.extraInfo
            return true
        }
        return false
    }

    /**
     * Searches for a client by ID.
     * @param clientId The ID of the client to search for.
     * @return The client if found, null otherwise.
     */
    fun searchClientById(clientId: Int) = clients.find { client -> client.clientId == clientId }

    /**
     * Searches for clients by first name.
     * @param searchString The string to search for within client first names.
     * @return A formatted string containing the list of matching clients.
     */
    fun searchClientByFirstName(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.firstName.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for clients by last name.
     * @param searchString The string to search for within client last names.
     * @return A formatted string containing the list of matching clients.
     */
    fun searchClientByLastName(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.lastName.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for appointments by ID.
     * @param searchInt The ID of the appointment to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentById(searchInt: Int): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.appointmentId == searchInt) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchInt"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Searches for clients by address.
     * @param searchString The string to search for within client addresses.
     * @return A formatted string containing the list of matching clients.
     */
    fun searchClientByAddress(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.address.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for clients by phone number.
     * @param searchInt The phone number to search for.
     * @return A formatted string containing the list of matching clients.
     */
    fun searchClientByPhone(searchInt: Int) =
        Utilities.formatListString(
            clients.filter { client -> client.phone == searchInt }
        )

    /**
     * Searches for appointments by date.
     * @param searchString The string representing the date to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentByDate(searchString: String): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.date.contains(searchString)) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchString"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Searches for appointments by treatment categories.
     * @param searchString The string representing the treatment category to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentByCategories(searchString: String): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.treatment.contains(searchString)) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchString"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Searches for appointments by price.
     * @param searchInt The price to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentByPrice(searchInt: Int): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.price == searchInt) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchInt"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Searches for appointments by review rating.
     * @param searchInt The review rating to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentByReview(searchInt: Int): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.review == searchInt) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchInt"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Searches for clients by email address.
     * @param searchString The string to search for within client email addresses.
     * @return A formatted string containing the list of matching clients.
     */
    fun searchClientByEmail(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.email.contains(searchString, ignoreCase = true) }
        )


    /**
     * Searches for appointments by time.
     * @param searchDouble The time to search for.
     * @return A formatted string containing the list of matching appointments.
     */
    fun searchAppointmentByTime(searchDouble: Double): String {
        return if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfClients = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.time == searchDouble) {
                        listOfClients += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfClients == "") {
                "No items found for: $searchDouble"
            } else {
                listOfClients
            }
        }
    }

    /**
     * Finds a client by index.
     * @param index The index of the client to find.
     * @return The client if found, null otherwise.
     */
    fun findClient(index: Int): Client? {
        return if (Utilities.isValidListIndex(index, clients)) {
            clients[index]
        } else {
            null
        }
    }

    /**
     * Deletes a client by ID.
     * @param id The ID of the client to delete.
     * @return true if the client is successfully deleted, false otherwise.
     */
    fun deleteClient(id: Int) = clients.removeIf { client -> client.clientId == id }

    /**
     * Clears all stored clients.
     */
    fun clearAllClients() = clients.clear()

    /**
     * Checks if there are clients stored.
     * @return A message indicating whether there are clients stored or not.
     */
    fun checkIfThereAreClients(): String {
        if (clients.isNotEmpty()) {
            return "Currently there are clients stored"
        } else if (clients.isEmpty()) {
            return "Currently there are no clients stored"
        } else {
            return "Error"
        }
    }
    ////////////////functions for appointment

    /**
     * Lists all scheduled appointments for clients.
     * @return A formatted string containing the list of scheduled appointments.
     */
    fun listScheduledAppointments(): String =
        if (numberOfClients() == 0) {
            "No clients stored"
        } else {
            var listOfScheduledAppointments = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.isScheduled) {
                        listOfScheduledAppointments += "${client.firstName} ${client.lastName}: ${appointment.date}${appointment.treatment}\n"
                    }
                }
            }
            listOfScheduledAppointments
        }
    /**
     * Loads client data from the serializer.
     * @throws Exception if an error occurs during loading.
     */
    @Throws(Exception::class)
    fun load() {
        clients = serializer.read() as ArrayList<Client>
    }

    /**
     * Stores client data using the serializer.
     * @throws Exception if an error occurs during storing.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(clients)
    }

}