package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.Appointment
import models.Client
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * JSONSerializer provides functionality to serialize and deserialize objects to/from JSON format.
 *
 * @property file The file to read from or write to.
 */
class JSONSerializer(private val file: File) : Serializer {

    /**
     * Reads serialized data from the file and deserializes it.
     *
     * @return The deserialized object.
     * @throws Exception if an error occurs during reading or deserialization.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
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
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
