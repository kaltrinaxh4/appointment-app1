package models

import persistence.JSONSerializer

data class Client(
    val jsonSerializer: JSONSerializer,
    val clientId: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: Address
) {

    data class Address(
        val town: String,
        val street: String,
        val city: String
    )
}

data class Appointment(
    val appointmentId: Int,
    val client: Client,
    val serviceType: String,
    val appointmentDateTime: String,  // Assuming your date is represented as a String
    val appointmentDuration: String,
    val appointmentCostPaid: Double,
    val appointmentSatisfaction: Int,
    var isAppointmentCancelled: Boolean = false  // Added the isAppointmentCancelled field
)

class AppointmentService {

    private var appointments = ArrayList<Appointment>()

    fun add(appointment: Appointment): Boolean {
        return appointments.add(appointment)
    }

    fun deleteAppointment(indexToDelete: Int): Appointment? {
        return if (isValidListIndex(indexToDelete, appointments)) {
            appointments.removeAt(indexToDelete)
        } else null
    }

    private fun isValidListIndex(indexToDelete: Int, appointments: ArrayList<Appointment>): Boolean {
        return indexToDelete in 0 until appointments.size
    }
    fun updateAppointment(indexToUpdate: Any?, note: Appointment?): Boolean {
        //find the note object by the index number
        val foundAppointment = findAppointment(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        val appointment = null
        if ((foundAppointment != null) && (appointment != null)) {
            val also = appointment.appointmentId.also {
                val it = ""
                foundAppointment.appointmentID = it
            }

            foundAppointment.appointmentClient = appointment.appointmentClient
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }



    fun cancelAppointment(indexToCancel: Int): Boolean {
        if (isValidListIndex(indexToCancel, appointments)) {
            val appointmentToCancel = appointments[indexToCancel]
            if (!appointmentToCancel.isAppointmentCancelled) {
                appointmentToCancel.isAppointmentCancelled = true
                return true
            }
        }
        return false
    }

    fun listAllAppointments(): String =
        if (appointments.isEmpty()) "No appointments stored"
        else formatListString(appointments)

    fun listActiveAppointments(): String =
        if (numberOfActiveAppointments() == 0) "No active appointments stored"
        else formatListString(appointments.filter { appointment -> !appointment.isAppointmentCancelled })

    fun listCancelledAppointments(): String =
        if (numberOfCancelledAppointments() == 0) "No cancelled appointments stored"
        else formatListString(appointments.filter { appointment -> appointment.isAppointmentCancelled })

    private fun numberOfCancelledAppointments(): Int {
        return appointments.count { appointment -> appointment.isAppointmentCancelled }
    }

    fun listAppointmentsBySelectedPriority(priority: Int): String =
        if (appointments.isEmpty()) "No appointments stored"
        else {
            val listOfAppointments = formatListString(appointments.filter { appointment -> appointment.appointmentSatisfaction == priority })
            if (listOfAppointments.isEmpty()) "No appointments with satisfaction level: $priority"
            else "${numberOfAppointmentsByPriority(priority)} appointments with satisfaction level $priority: $listOfAppointments"
        }

    fun numberOfAppointments(): Int = appointments.size

    fun numberOfActiveAppointments(): Int = appointments.count { appointment: Appointment -> !appointment.isAppointmentCancelled }

    private fun numberOfAppointmentsByPriority(priority: Int): Int = appointments.count { p: Appointment -> p.appointmentSatisfaction == priority }

    fun searchByID(searchString: Any): String =
        formatListString(appointments.filter { appointment -> appointment.appointmentId.toString().contains(searchString.toString(), ignoreCase = true) })

    fun findAppointment(index: Int): Appointment? {
        return if (isValidListIndex(index, appointments)) {
            appointments[index]
        } else null
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, appointments)
    }

    @Throws(Exception::class)
    fun load() {
        val serializer = null
        // TODO: Add logic for loading appointments from serializer
    }

    @Throws(Exception::class)
    fun store() {
        val serializer = null
        // TODO: Add logic for storing appointments using serializer
    }

    private fun formatListString(appointmentsToFormat: List<Appointment>): String =
        appointmentsToFormat
            .joinToString(separator = "\n") { appointment ->
                appointments.indexOf(appointment).toString() + ": " + appointment.toString()
            }
}

