package models

import utils.Utilities

data class Client(
    var clientId: Int = 0,
    var firstName: String,
    var lastName: String,
    var street: String,
    var county: String,
    var email: String,
    var phone: Int,
    var allergy: String,
    var hasPaid: Boolean,
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

    fun checkifAppointmentIsScheduled(): Boolean {
        if (appointments.isNotEmpty()) {
            for (appointment in appointments) {
                if (!appointment.isScheduled) {

                    return false
                }
            }
        }
        return true
    }
}