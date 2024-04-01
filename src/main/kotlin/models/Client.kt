package models

import utils.Utilities

data class Client(
    var clientId: Int = 0,
    var firstName: String,
    var lastName: String,
    var address: String,
    var email: String,
    var phone: Int,
    var extraInfo: String,

    var appointments: MutableSet<Appointment> = mutableSetOf()
) {

    fun addAppointment(appointment: Appointment): Boolean {
        appointment.appointmentId = getAppointmentId()
        return appointments.add(appointment)
    }

    private var lastAppointmentId = 0
    private fun getAppointmentId() = lastAppointmentId++

    fun listAppointments(): String =
        if (appointments.isEmpty()) "\tNO APPOINTMENTS FOUND"
        else Utilities.formatSetString(appointments)

    fun updateAppointment(id: Int, newAppointment: Appointment): Boolean {
        val foundAppointment = findAppointmentById(id)

        if (foundAppointment != null) {
            foundAppointment.time = newAppointment.time
            foundAppointment.date = newAppointment.date
            foundAppointment.treatment = newAppointment.treatment
            foundAppointment.price = newAppointment.price
            foundAppointment.review = newAppointment.review
            return true
        }

        return false
    }

    fun findAppointmentById(id: Int): Appointment? {
        return appointments.find { appointment -> appointment.appointmentId == id }
    }

    fun numberOfAppointments(): Int = appointments.size

    fun deleteAppointment(id: Int): Boolean {
        return appointments.removeIf { appointment -> appointment.appointmentId == id }
    }



    override fun toString(): String {
        return "$clientId: $firstName $lastName, Address($address), Email($email), Phone($phone), ExtraInfo($extraInfo) \n${listAppointments()}"
    }

}
