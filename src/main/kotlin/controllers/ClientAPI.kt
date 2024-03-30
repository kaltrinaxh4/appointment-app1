package controllers

import models.Client
import persistence.Serializer
import utils.Utilities
import java.util.ArrayList

class ClientAPI(serializerType: Serializer) {

    private var clients = ArrayList<Client>()
    private var serializer: Serializer = serializerType
    private var lastClientId = 0

    /////////////////////////functions for client
    fun addClient(client: Client): Boolean {
        client.clientId = getClientId()
        return clients.add(client)
    }

    private fun getClientId() = lastClientId++

    fun listAllClients() =
        if (clients.isEmpty()) {
            "No clients stored"
        } else {
            Utilities.formatListString(clients)
        }

    fun numberOfClients() = clients.size

    fun updateClient(id: Int, client: Client?): Boolean {
        val foundClient = findClientById(id)
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

    fun findClientById(clientId: Int) = clients.find { client -> client.clientId == clientId }

    fun searchClientByFirstName(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.firstName.contains(searchString, ignoreCase = true) }
        )

    fun searchClientByLastName(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.lastName.contains(searchString, ignoreCase = true) }
        )

    fun searchClientByAddress(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.address.contains(searchString, ignoreCase = true) }
        )


    fun searchClientByPhone(searchInt: Int) =
        Utilities.formatListString(
            clients.filter { client -> client.phone == searchInt }
        )

    fun findClient(index: Int): Client? {
        return if (Utilities.isValidListIndex(index, clients)) {
            clients[index]
        } else {
            null
        }
    }

    fun deleteClient(id: Int) = clients.removeIf { client -> client.clientId == id }

    fun clearAllClients() = clients.clear()

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
    @Throws(Exception::class)
    fun load() {
        clients = serializer.read() as ArrayList<Client>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(clients)
    }

}