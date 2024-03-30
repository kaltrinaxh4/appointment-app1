package utils

import models.Appointment
import models.Client

object Utilities {
    @JvmStatic
    fun formatListString(clientsToFormat: List<Client>): String =
        clientsToFormat.joinToString(separator = "\n") { client -> "$client" }

    @JvmStatic
    fun formatSetString(appointmentsToFormat: Set<Appointment>): String =
        appointmentsToFormat.joinToString(separator = "\n") { appointment -> "\t$appointment" }

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    fun validRangeDouble(numberToCheck: Double, min: Double, max: Double): Boolean {
        return numberToCheck in min..max
    }
}
