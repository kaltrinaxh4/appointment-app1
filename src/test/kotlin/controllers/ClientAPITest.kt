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
import persistence.JSONSerializer

class ClientAPITest {

    private var Kylie: Client? = null
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

        Kylie = Client(
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

        populatedClients?.addClient(Kylie!!)
        populatedClients?.addClient(mark!!)
        populatedClients?.addClient(emma!!)
        populatedClients?.addClient(david!!)
        populatedClients?.addClient(sophie!!)
    }

    @AfterEach
    fun tearDown() {
        Kylie = null
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
            assertEquals(Kylie, result)
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
                    "Kylie",
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

            @Test
            fun `clearAllClients should remove all clients`() {
                val initialSize = populatedClients!!.numberOfClients()

                populatedClients!!.clearAllClients()

                val finalSize = populatedClients!!.numberOfClients()

                assertEquals(0, finalSize)
                assertEquals(
                    initialSize,
                    finalSize + 5
                )
            }
        }

        @Nested
        inner class SearchAppointments {
            @Test
            fun `searchAppointmentByDate should return appointments for given date`() {
                val result = populatedClients?.searchAppointmentByDate("15/12/2023")
                assertTrue(result?.contains("Kylie Smith") ?: false)
                assertTrue(result?.contains("Emma Brown") ?: false)
                assertTrue(result?.contains("David Jones") ?: false)
                assertTrue(result?.contains("Sophie Williams") ?: false)
            }

            @Test
            fun `searchAppointmentByCategories should return appointments for given category`() {
                val result = populatedClients?.searchAppointmentByCategories("Hairdye")
                assertTrue(result?.contains("Emma Brown") ?: false)
            }

            @Test
            fun `searchAppointmentByPrice should return appointments for given price`() {
                val result = populatedClients?.searchAppointmentByPrice(25)
                assertTrue(result?.contains("David Jones") ?: false)
            }

            @Test
            fun `searchAppointmentByReview should return appointments for given review`() {
                val result = populatedClients?.searchAppointmentByReview(5)
                assertTrue(result?.contains("Mark Taylor") ?: false)
                assertTrue(result?.contains("Sophie Williams") ?: false)
            }

            @Test
            fun `searchAppointmentByTime should return appointments for given time`() {
                val result = populatedClients?.searchAppointmentByTime(11.0)
                assertTrue(result?.contains("Mark Taylor") ?: false)
            }
        }

        @Test
        fun `listScheduledAppointments should return all scheduled appointments`() {
            val result = populatedClients?.listScheduledAppointments()
            assertTrue(result?.contains("Kylie Smith") ?: false)
            assertTrue(result?.contains("Mark Taylor") ?: false)
            assertTrue(result?.contains("Sophie Williams") ?: false)
        }


        @Test
        fun `checkIfThereAreClients should return appropriate message when there are clients`() {
            val result = populatedClients?.checkIfThereAreClients()
            assertEquals("Currently there are clients stored", result ?: "")
        }

        @Test
        fun `checkIfThereAreClients should return appropriate message when there are no clients`() {
            populatedClients?.clearAllClients()
            val result = populatedClients?.checkIfThereAreClients()
            assertEquals("Currently there are no clients stored", result ?: "")
        }

    }//
    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty clients.XML file.
            val storingClients = ClientAPI(XMLSerializer(File("clients.xml")))
            storingClients.store()

            //Loading the empty notes.xml file into a new object
            val loadedClients = ClientAPI(XMLSerializer(File("clients.xml")))
            loadedClients.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingClients.numberOfClients())
            assertEquals(0, loadedClients.numberOfClients())
            assertEquals(storingClients.numberOfClients(), loadedClients.numberOfClients())
        }


        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingClients = ClientAPI(JSONSerializer(File("clients.json")))
            storingClients.store()

            //Loading the empty notes.json file into a new object
            val loadedClients = ClientAPI(JSONSerializer(File("clients.json")))
            loadedClients.load()

            //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(0, storingClients.numberOfClients())
            assertEquals(0, loadedClients.numberOfClients())
            assertEquals(storingClients.numberOfClients(), loadedClients.numberOfClients())
        }


    }
}




