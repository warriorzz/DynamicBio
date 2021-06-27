package com.github.warriorzz.backend.core

import de.nycode.bcrypt.hash
import de.nycode.bcrypt.verify
import org.litote.kmongo.eq
import java.nio.charset.Charset
import java.util.*
import kotlin.random.Random

object Auth {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private const val size = 42

    internal suspend fun getNewSecret(userId: Long) : String {
        val userData = Database.userCollection.find(UserData::discordId eq userId).first()
        val newRandomString = generateNewRandomString().base64encode()
        if (userData == null) {
            Database.userCollection.insertOne(UserData(userId, hash(newRandomString).toString()))
        } else {
            Database.userCollection.updateOne(UserData::discordId eq userId, UserData(userId, hash(newRandomString).toString()))
        }
        return newRandomString
    }

    internal suspend fun verifySecret(userId: Long, value: String) : Boolean {
        return Database.userCollection.findOne(UserData::discordId eq userId)?.let { verify(value, it.hash.toByteArray(
            Charset.defaultCharset())) } ?: false
    }

    private fun generateNewRandomString() : String{
        var string = ""
        (0 until size).forEach { _ ->
            string += charPool[Random.nextInt(charPool.size)]
        }
        return string
    }

}

fun String.base64encode(): String =
    Base64.getEncoder().encodeToString(this.toByteArray())
