package models

import utils.Utilities

/**
 * Represents a client.
 *
 * @property clientId The unique identifier for the client.
 * @property firstName The first name of the client.
 * @property lastName The last name of the client.
 * @property address The address of the client.
 * @property email The email address of the client.
 * @property phone The phone number of the client.
 * @property extraInfo Additional information about the client.
 * @property appointments The set of appointments associated with the client.
 */
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

    /**
     * Adds an appointment for the client.
     *
     * @param appointment The appointment object to add.
     * @return true if the appointment was successfully added, false otherwise.
     */
    fun addAppointment(appointment: Appointment): Boolean {
        appointment.appointmentId = getAppointmentId()
        return appointments.add(appointment)
    }

    private var lastAppointmentId = 0

    /**
     * Generates a new appointment ID.
     *
     * @return The newly generated appointment ID.
     */
    private fun getAppointmentId() = lastAppointmentId++

    /**
     * Lists all appointments associated with the client.
     *
     * @return A formatted string listing all appointments, or a message indicating no appointments are found.
     */
    fun listAppointments(): String =
        if (appointments.isEmpty()) "\tNO APPOINTMENTS FOUND"
        else Utilities.formatSetString(appointments)

    /**
     * Updates an appointment associated with the client.
     *
     * @param id The ID of the appointment to update.
     * @param newAppointment The updated appointment object.
     * @return true if the appointment was successfully updated, false otherwise.
     */
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

    /**
     * Finds an appointment by its ID.
     *
     * @param id The ID of the appointment to find.
     * @return The appointment object if found, null otherwise.
     */
    fun findAppointmentById(id: Int): Appointment? {
        return appointments.find { appointment -> appointment.appointmentId == id }
    }

    /**
     * Returns the number of appointments associated with the client.
     *
     * @return The number of appointments.
     */
    fun numberOfAppointments(): Int = appointments.size

    /**
     * Deletes an appointment associated with the client.
     *
     * @param id The ID of the appointment to delete.
     * @return true if the appointment was successfully deleted, false otherwise.
     */
    fun deleteAppointment(id: Int): Boolean {
        return appointments.removeIf { appointment -> appointment.appointmentId == id }
    }

    /**
     * Converts the client object to a string representation.
     *
     * @return A string representation of the client.
     */
    override fun toString(): String {
        return "$clientId: $firstName $lastName, Address($address), Email($email), Phone($phone), ExtraInfo($extraInfo) \n${listAppointments()}"
    }

}
