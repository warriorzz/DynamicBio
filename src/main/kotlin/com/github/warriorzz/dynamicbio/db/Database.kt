package com.github.warriorzz.dynamicbio.db

import com.github.warriorzz.dynamicbio.config.Config
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
    lateinit var biographieCollection: CoroutineCollection<Biography>
        private set

    operator fun invoke() {
        client = KMongo.createClient(
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString("mongodb://${Config.DATABASE_USER}:${Config.DATABASE_PASSWORD}@${Config.DATABASE_HOST}/${Config.DATABASE_NAME}?retryWrites=true&w=majority"))
                .build()
        ).coroutine

        database = client.getDatabase(Config.DATABASE_NAME)
        biographieCollection = database.getCollection()
        databaseScope.launch {
            if (biographieCollection.find().toList().isEmpty()) {
                biographieCollection.insertOne(Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter"))
            }
        }
    }
}

val databaseScope = CoroutineScope(Dispatchers.IO)
