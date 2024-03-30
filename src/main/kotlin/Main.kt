import controllers.ClientAPI
import models.Appointment
import models.Client
import persistence.XMLSerializer
import java.io.File

private val clientAPI = ClientAPI(XMLSerializer(File("notes.xml")))