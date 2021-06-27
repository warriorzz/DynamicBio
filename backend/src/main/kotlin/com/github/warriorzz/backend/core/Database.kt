package com.github.warriorzz.backend.core

import com.github.warriorzz.backend.config.Config
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.UuidRepresentation
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object Database {
    private lateinit var client: CoroutineClient
    lateinit var database: CoroutineDatabase
        private set
    lateinit var userCollection: CoroutineCollection<UserData>
        private set

    operator fun invoke() {
        client = KMongo.createClient(
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(Config.DATABASE_CONNECTION_STRING))
                .build()
        ).coroutine

        database = client.getDatabase(Config.DATABASE_NAME)
        userCollection = database.getCollection()
    }
}

@Serializable
data class UserData(val discordId: Long, @Serializable(with = ByteArraySerializer::class) val hash: ByteArray)

object ByteArraySerializer : KSerializer<ByteArray> {
    override fun deserialize(decoder: Decoder): ByteArray {
        return decoder.decodeString().toByteArray(Charsets.UTF_8)
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ByteArrayAsString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ByteArray) {
        encoder.encodeString(value.decodeToString())
    }
}