package controllers

import models.Client
import utils.Utilities.formatListString
import java.util.ArrayList

class ClientAPI() {

    private var clients = ArrayList<Client>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun add(client: Client): Boolean {
        client.clientId = getId()
        return clients.add(client)
    }

    fun delete(id: Int) = clients.removeIf { client -> client.clientId == id }

    fun update(id: Int, updatedClient: Client?): Boolean {
        // find the client object by the id
        val foundClient = findClient(id)

        // if the client exists, use the details passed as parameters to update the found client in the ArrayList.
        if ((foundClient != null) && (updatedClient != null)) {
            foundClient.firstName = updatedClient.firstName
            foundClient.lastName = updatedClient.lastName
            foundClient.phoneNumber = updatedClient.phoneNumber
            foundClient.address = updatedClient.address
            return true
        }

        // if the client was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveClient(id: Int): Boolean {
        val foundClient = findClient(id)
        if ((foundClient != null) && (!foundClient.isClientArchived)) {
            foundClient.isClientArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun listAllClients() =
        if (clients.isEmpty()) "No clients stored"
        else formatListString(clients)

    fun listActiveClients() =
        if (numberOfActiveClients() == 0) "No active clients stored"
        else formatListString(clients.filter { client -> !client.isClientArchived })

    fun listArchivedClients() =
        if (numberOfArchivedClients() == 0) "No archived clients stored"
        else formatListString(clients.filter { client -> client.isClientArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun numberOfClients() = clients.size
    fun numberOfArchivedClients(): Int = clients.count { client: Client -> client.isClientArchived }
    fun numberOfActiveClients(): Int = clients.count { client: Client -> !client.isClientArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findClient(clientId: Int) = clients.find { client -> client.clientId == clientId }

    fun searchClientsByName(searchString: String) =
        formatListString(
            clients.filter { client ->
                client.firstName.contains(searchString, ignoreCase = true) ||
                        client.lastName.contains(searchString, ignoreCase = true)
            }
        )

    fun searchAppointmentsByServiceType(searchString: String): String {
        return if (numberOfClients() == 0) "No clients stored"
        else {
            var listOfAppointments = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    if (appointment.serviceType.contains(searchString, ignoreCase = true)) {
                        listOfAppointments += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                    }
                }
            }
            if (listOfAppointments == "") "No appointments found for: $searchString"
            else listOfAppointments
        }
    }/

    // ----------------------------------------------
    //  LISTING METHODS FOR APPOINTMENTS
    // ----------------------------------------------
    fun listAppointments(): String =
        if (numberOfClients() == 0) "No clients stored"
        else {
            var listOfAppointments = ""
            for (client in clients) {
                for (appointment in client.appointments) {
                    listOfAppointments += "${client.clientId}: ${client.firstName} ${client.lastName} \n\t${appointment}\n"
                }
            }
            listOfAppointments
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR APPOINTMENTS
    // ----------------------------------------------
    fun numberOfAppointments(): Int {
        var numberOfAppointments = 0
        for (client in clients) {
            numberOfAppointments += client.appointments.size
        }
        return numberOfAppointments
    }

}
