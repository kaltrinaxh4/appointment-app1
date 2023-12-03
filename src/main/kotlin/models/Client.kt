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