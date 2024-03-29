package controllers

import models.Client
import persistence.Serializer
import utils.Utilities
import java.util.ArrayList

class ClientAPI(serializerType: Serializer) {

    private var clients = ArrayList<Client>()
    private var serializer: Serializer = serializerType
    private var lastClientId = 0

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

    fun searchClientByEmail(searchString: String) =
        Utilities.formatListString(
            clients.filter { client -> client.email.contains(searchString, ignoreCase = true) }
        )

    fun searchClientByPhone(searchInt: Int) =
        Utilities.formatListString(
            clients.filter { client -> client.phone == searchInt }
        )
}
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
