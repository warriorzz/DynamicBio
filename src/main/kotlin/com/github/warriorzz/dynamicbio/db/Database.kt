package com.github.warriorzz.dynamicbio.db

import com.github.warriorzz.dynamicbio.config.Config
import com.github.warriorzz.dynamicbio.config.Environment
import com.github.warriorzz.dynamicbio.model.Biography
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    lateinit var biographyCollection: CoroutineCollection<Biography>
        private set

    operator fun invoke() {
        client = KMongo.createClient(
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(getConnectionString()))
                .build()
        ).coroutine

        database = client.getDatabase(Config.DATABASE_NAME)
        biographyCollection = database.getCollection()
        databaseScope.launch {
            if (biographyCollection.countDocuments() == 0L) {
                biographyCollection.insertOne(Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter"))
            }
        }
    }

    private fun getConnectionString() : String {
        val default = "${Config.DATABASE_USER}:${Config.DATABASE_PASSWORD}@${Config.DATABASE_HOST}/${Config.DATABASE_NAME}?retryWrites=true&w=majority"
        return if(Config.ENVIRONMENT == Environment.DEVELOPMENT)
            "mongodb+srv://$default"
        else "mongodb://$default"
    }
}

val databaseScope = CoroutineScope(Dispatchers.IO)
