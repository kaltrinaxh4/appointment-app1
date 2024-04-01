package persistence

/**
 * XMLSerializer is responsible for serializing and deserializing objects to/from XML format.
 *
 * @property file The file to read from or write to.
 */
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Appointment
import models.Client
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class XMLSerializer(private val file: File) : Serializer {

    /**
     * Reads serialized data from the file and deserializes it.
     *
     * @return The deserialized object.
     * @throws Exception if an error occurs during reading or deserialization.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Client::class.java))
        xStream.allowTypes(arrayOf(Appointment::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes the provided object and writes it to the file.
     *
     * @param obj The object to serialize and write.
     * @throws Exception if an error occurs during serialization or writing.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
