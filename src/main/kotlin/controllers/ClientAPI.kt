package controllers

import models.Client
import persistence.Serializer
import utils.Utilities
import java.util.ArrayList

class ClientAPI(serializerType: Serializer) {



    fun addClient(client: Client): Boolean {
        client.clientId = getClientId()
        return clients.add(client)
    }