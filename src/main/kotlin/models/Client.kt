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
    val appointmentDateTime: String,
    val appointmentDuration: String,
    val appointmentCostPaid: Double,
    val appointmentSatisfaction: Int
)
//  private var serializer: Serializer = serializerType

private var appointments = ArrayList<Appointment>()

fun add(appointment: Appointment): Boolean {
    return appointments.add(appointment)
}

fun deleteAppointment(indexToDelete: Any?): Appointment? {
    return if (indexToDelete is Int && isValidListIndex(indexToDelete, appointments)) {
        appointments.removeAt(indexToDelete)
    } else null
}

private fun isValidListIndex(indexToDelete: Int, appointments: ArrayList<Appointment>): Boolean {

    return TODO("Provide the return value")
}

fun updateAppointment(indexToUpdate: Any?, note: Appointment?): Boolean {
    //find the note object by the index number
    val foundAppointmentID = findAppointment(indexToUpdate)

    //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
    val appointment = null
    if ((foundAppointmentID != null) && (appointment != null)) {
        val also = appointment.appointmentID.also {
            val it = ""
            foundAppointmentID.appointmentID = it
        }
        foundAppointmentID.appointmentPriority = appointment.appointmentPriority
        foundAppointmentID.appointmentClient = appointment.appointmentClient
        return true
    }

    //if the note was not found, return false, indicating that the update was not successful
    return false
}
fun cancelAppointment(indexToCancel: Any?): Boolean {
    if (isValidIndex(indexToCancel)) {
        val appointmentToCancel = appointments[indexToCancel]
        if (!appointmentToCancel.isAppointmentCancelled) {
            appointmentToCancel.isAppointmentCancelled = true
            return true
        }
    }
    return false
}

fun listAllAppointments(): String =
    if (appointments.isEmpty())  "No appointments stored"
    else formatListString(appointments)

fun listActiveAppointments(): String =
    if (numberOfActiveAppointments() == 0) "No active appointments stored"
    else formatListString(appointments.filter{ appointment -> !appointment.isAppointmentCancelled })

fun listCancelledAppointments(): String =
    if (numberOfCancelledAppointments() == 0) "No cancelled appointments stored"
    else formatListString(appointments.filter{ appointment -> appointment.isAppointmentCancelled })

private fun numberOfCancelledAppointments(): Any {

}
