
package controllers

import models.Appointment
import persistence.Serializer
import utils.Utilities.isValidListIndex

class AppointmentAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType

    private var appointments = ArrayList<Appointment>()

    fun add(appointment: Appointment): Boolean {
        return appointments.add(appointment)
    }

    fun deleteAppointment(indexToDelete: Int): Appointment? {
        return if (isValidListIndex(indexToDelete, appointments)) {
            appointments.removeAt(indexToDelete)
        } else null
    }

    fun updateAppointment(indexToUpdate: Int, note: Appointment?): Boolean {
        //find the note object by the index number
        val foundAppointment = findAppointment(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundAppointment != null) && (appointment != null)) {
            foundAppointment.appointmentID = appointment.appointmentID
            foundAppointment.appointmentPriority = appointment.appointmentPriority
            foundAppointment.appointmentClient = appointment.appointmentClient
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun cancelAppointment(indexToCancel: Int): Boolean {
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

    fun listAppointmentsBySelectedPriority(priority: Int): String =
        if (appointments.isEmpty()) "No appointments stored"
        else {
            val listOfAppointments = formatListString(appointments.filter{ appointment -> appointment.appointmentPriority == priority})
            if (listOfAppointments.equals("")) "No appointments with priority: $priority"
            else "${numberOfAppointmentsByPriority(priority)} appointments with priority $priority: $listOfAppointments"
        }

    fun numberOfAppointments(): Int = appointments.size
    fun numberOfActiveAppointments(): Int = appointments.count{appointment: Appointment -> !appointment.isAppointmentArchived}
    fun numberOfArchivedAppointments(): Int = appointments.count{appointment: Appointment -> appointment.isAppointmentArchived}
    fun numberOfAppointmentsByPriority(priority: Int): Int = appointments.count { p: Appointment -> p.appointmentPriority == priority }

    fun searchByID(searchString : String) =
        formatListString(appointments.filter { appointment -> appointment.noteTitle.contains(searchString, ignoreCase = true)})

    fun findAppointment(index: Int): Appointment? {
        return if (isValidListIndex(index, appointments)) {
            appointments[index]
        } else null
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, appointments);
    }

    @Throws(Exception::class)
    fun load() {
        appointments = serializer.read() as ArrayList<Appointment>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(appointments)
    }

    private fun formatListString(notesToFormat : List<Appointment>) : String =
        notesToFormat
            .joinToString (separator = "\n") { appointment ->
                appointments.indexOf(appointment).toString() + ": " + appointment.toString() }

}