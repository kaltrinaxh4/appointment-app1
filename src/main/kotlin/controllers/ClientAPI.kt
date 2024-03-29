package controllers

import models.Client
import persistence.Serializer
import utils.Utilities
import java.util.ArrayList

class ClientAPI(serializerType: Serializer) {

    private var clients = ArrayList<Client>()
    private var serializer: Serializer = serializerType

    fun addClient(client: Client): Boolean {
        client.clientId = getClientId()
        return clients.add(client)
    }

    private var lastClientId = 0
    fun getClientId() = lastClientId++

    fun listAllClients() =
        if (clients.isEmpty()) {
            "No clients stored"
        } else {
            Utilities.formatListString(clients)
        }
}