package com.emarketing_paradice.gnsrilanka.data.repository

import android.content.Context
import com.emarketing_paradice.gnsrilanka.data.model.BirthRecord
import com.emarketing_paradice.gnsrilanka.data.model.Citizen
import com.emarketing_paradice.gnsrilanka.data.model.DeathRecord
import com.emarketing_paradice.gnsrilanka.data.model.Household
import com.emarketing_paradice.gnsrilanka.data.model.MovementLog
import com.emarketing_paradice.gnsrilanka.data.model.OfficerProfile
import com.emarketing_paradice.gnsrilanka.data.model.Request
import com.emarketing_paradice.gnsrilanka.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FileRepository(private val context: Context) {
    private val gson = Gson()

    private fun getFile(fileName: String): File {
        return File(context.filesDir, fileName)
    }

    private fun <T> readFromFile(fileName: String, typeToken: TypeToken<T>): T? {
        val file = getFile(fileName)
        if (!file.exists()) return null
        return try {
            val json = file.readText()
            gson.fromJson(json, typeToken.type)
        } catch (e: Exception) {
            null
        }
    }

    private fun <T> writeToFile(fileName: String, data: T) {
        val file = getFile(fileName)
        val json = gson.toJson(data)
        file.writeText(json)
    }

    // Users
    fun getUsers(): List<User> {
        val type = object : TypeToken<List<User>>() {}
        return readFromFile("users.json", type) ?: emptyList()
    }

    fun saveUsers(users: List<User>) {
        writeToFile("users.json", users)
    }

    fun saveUser(user: User) {
        val users = getUsers().toMutableList()
        users.removeAll { it.nic == user.nic }
        users.add(user)
        saveUsers(users)
    }

    // Households
    fun getHouseholds(): List<Household> {
        val type = object : TypeToken<List<Household>>() {}
        return readFromFile("households.json", type) ?: emptyList()
    }

    fun saveHouseholds(households: List<Household>) {
        writeToFile("households.json", households)
    }

    fun saveHousehold(household: Household) {
        val households = getHouseholds().toMutableList()
        households.removeAll { it.id == household.id }
        households.add(household)
        saveHouseholds(households)
    }

    // Citizens
    fun getCitizens(): List<Citizen> {
        val type = object : TypeToken<List<Citizen>>() {}
        return readFromFile("citizens.json", type) ?: emptyList()
    }

    fun saveCitizens(citizens: List<Citizen>) {
        writeToFile("citizens.json", citizens)
    }

    fun saveCitizen(citizen: Citizen) {
        val citizens = getCitizens().toMutableList()
        citizens.removeAll { it.nic == citizen.nic }
        citizens.add(citizen)
        saveCitizens(citizens)
    }

    // Requests
    fun getRequests(): List<Request> {
        val type = object : TypeToken<List<Request>>() {}
        return readFromFile("requests.json", type) ?: emptyList()
    }

    fun saveRequests(requests: List<Request>) {
        writeToFile("requests.json", requests)
    }

    fun saveRequest(request: Request) {
        val requests = getRequests().toMutableList()
        requests.removeAll { it.id == request.id }
        requests.add(request)
        saveRequests(requests)
    }

    // Officer Profile
    fun getOfficerProfile(nic: String): OfficerProfile? {
        val type = object : TypeToken<OfficerProfile>() {}
        return readFromFile("profile_$nic.json", type)
    }

    fun saveOfficerProfile(nic: String, profile: OfficerProfile) {
        writeToFile("profile_$nic.json", profile)
    }

    // Birth Records
    fun getBirthRecords(): List<BirthRecord> {
        val type = object : TypeToken<List<BirthRecord>>() {}
        return readFromFile("birth_records.json", type) ?: emptyList()
    }

    fun saveBirthRecords(records: List<BirthRecord>) {
        writeToFile("birth_records.json", records)
    }

    fun saveBirthRecord(record: BirthRecord) {
        val records = getBirthRecords().toMutableList()
        records.removeAll { it.recordId == record.recordId }
        records.add(record)
        saveBirthRecords(records)
    }

    // Death Records
    fun getDeathRecords(): List<DeathRecord> {
        val type = object : TypeToken<List<DeathRecord>>() {}
        return readFromFile("death_records.json", type) ?: emptyList()
    }

    fun saveDeathRecords(records: List<DeathRecord>) {
        writeToFile("death_records.json", records)
    }

    fun saveDeathRecord(record: DeathRecord) {
        val records = getDeathRecords().toMutableList()
        records.removeAll { it.recordId == record.recordId }
        records.add(record)
        saveDeathRecords(records)
    }

    // Movement Logs
    fun getMovementLogs(): List<MovementLog> {
        val type = object : TypeToken<List<MovementLog>>() {}
        return readFromFile("movement_logs.json", type) ?: emptyList()
    }

    fun saveMovementLogs(logs: List<MovementLog>) {
        writeToFile("movement_logs.json", logs)
    }

    fun saveMovementLog(log: MovementLog) {
        val logs = getMovementLogs().toMutableList()
        logs.removeAll { it.logId == log.logId }
        logs.add(log)
        saveMovementLogs(logs)
    }
}
