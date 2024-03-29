
package models


data class Client(var clientId: Int = 0,
                  var firstName: String,
                  var lastName: String,
                  var street: String,
                  var county: String,
                  var email: String,
                  var phone: Int,
                  var allergy: String,
                  var hasPaid: Boolean,
                  var appointments : MutableSet<Appointment> = mutableSetOf()) {

    fun addAppointment(appointment: Appointment): Boolean {
        appointment.appointmentId = getAppointmentId()
        return appointments.add(appointment)
    }

    private var lastAppointmentId = 0
    private fun getAppointmentId() = lastAppointmentId++
}