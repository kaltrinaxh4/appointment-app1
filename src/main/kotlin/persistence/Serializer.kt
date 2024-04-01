package persistence

/**
 * Serializer interface defines methods for serializing and deserializing objects.
 */
interface Serializer {

    /**
     * Writes the provided object to a persistent storage.
     *
     * @param obj The object to write.
     * @throws Exception if an error occurs during writing.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads and deserializes an object from a persistent storage.
     *
     * @return The deserialized object, or null if no object is found.
     * @throws Exception if an error occurs during reading or deserialization.
     */
    @Throws(Exception::class)
    fun read(): Any?
}
