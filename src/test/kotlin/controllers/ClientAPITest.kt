import controllers.ClientAPI
import models.Appointment
import models.Client
import persistence.Serializer
import persistence.XMLSerializer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class ClientAPITest {

    private var joan: Client? = null
    private var mark: Client? = null
    private var emma: Client? = null
    private var david: Client? = null
    private var sophie: Client? = null
    private var populatedClients: ClientAPI? = null
    private var emptyClients: ClientAPI? = null

    @BeforeEach
    fun setup() {
        val xmlSerializer = XMLSerializer(File("clients.xml"))
        populatedClients = ClientAPI(xmlSerializer)
        emptyClients = ClientAPI(xmlSerializer)

        val appointmentOne = Appointment(0, "15/12/2023", 10.00, "Haircut", 30, 4, true)
        val appointmentTwo = Appointment(1, "15/12/2023", 11.00, "Blowdry", 40, 5, true)
        val appointmentThree = Appointment(2, "15/12/2023", 13.00, "Hairdye", 20, 3, false)
        val appointmentFour = Appointment(3, "15/12/2023", 14.00, "KeratinTreatment", 25, 2, true)
        val appointmentFive = Appointment(4, "15/12/2023", 15.00, "HairPerm", 50, 5, true)

        joan = Client(
            0,
            "Joan",
            "Smith",
            "1 High Street",
            "joan@hotmail.com",
            123456789,
            "Hair care",
            mutableSetOf(appointmentOne)
        )
        mark = Client(
            1,
            "Mark",
            "Taylor",
            "2 Main Road",
            "mark@hotmail.com",
            112345678,
            "Hair styling",
            mutableSetOf(appointmentTwo)
        )
        emma = Client(
            2,
            "Emma",
            "Brown",
            "3 Park Avenue",
            "emma@hotmail.com",
            111234567,
            "Hair treatments",
            mutableSetOf(appointmentThree)
        )
        david = Client(
            3,
            "David",
            "Jones",
            "4 Elm Street",
            "david@hotmail.com",
            111123456,
            "Hair coloring",
            mutableSetOf(appointmentFour)
        )
        sophie = Client(
            4,
            "Sophie",
            "Williams",
            "5 Queen's Road",
            "sophie@hotmail.com",
            1111112345,
            "Hair extensions",
            mutableSetOf(appointmentFive)
        )

        populatedClients?.addClient(joan!!)
        populatedClients?.addClient(mark!!)
        populatedClients?.addClient(emma!!)
        populatedClients?.addClient(david!!)
        populatedClients?.addClient(sophie!!)
    }

    @AfterEach
    fun tearDown() {
        joan = null
        mark = null
        emma = null
        david = null
        sophie = null
        populatedClients = null
        emptyClients = null
    }

    @Nested
    inner class AddClientTests {
        @Test
        fun `adding a client increases the number of clients`() {
            val initialCount = populatedClients?.numberOfClients() ?: 0
            val newClient = Client(
                5,
                "Laura",
                "Thomas",
                "6 Victoria Street",
                "laura@hotmail.com",
                555555555,
                "Hair care products",
                mutableSetOf()
            )
            populatedClients?.addClient(newClient)
            assertEquals(initialCount + 1, populatedClients?.numberOfClients())
        }
    }

    @Nested
    inner class SearchTests {

        // Testing searchClientById
        @Test
        fun `searching client by id should return correct client`() {
            val result = populatedClients?.searchClientById(0)
            assertEquals(joan, result)
        }

        @Test
        fun `searching client by non-existent id should return null`() {
            val result = populatedClients?.searchClientById(100)
            assertEquals(null, result)
        }


        @Test
        fun `searching client by non-existent first name should return empty result`() {
            val result = populatedClients?.searchClientByFirstName("John")
            assertEquals("", result)
        }


        @Nested
        inner class UpdateTests {
            @Test
            fun `updating client information should return true`() {
                val updatedClient = Client(
                    0,
                    "Joan",
                    "Brown",
                    "1 High Street",
                    "joan@hotmail.com",
                    123456789,
                    "Hair care and styling",
                    mutableSetOf()
                )
                val result = populatedClients?.updateClient(0, updatedClient)
                assertTrue(result ?: false)
            }

            @Nested
            inner class DeleteTests {
            }
            @Test
            fun `deleting an existing client should decrease the number of clients`() {
                val initialCount = populatedClients?.numberOfClients() ?: 0
                populatedClients?.deleteClient(0)
                assertEquals(initialCount - 1, populatedClients?.numberOfClients())
            }


            @Test
            fun `deleting a non-existent client should not change the number of clients`() {
                val initialCount = populatedClients?.numberOfClients() ?: 0
                populatedClients?.deleteClient(100)
                assertEquals(initialCount, populatedClients?.numberOfClients())
            }


        }
    }
}
