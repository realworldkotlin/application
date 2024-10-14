package _8_Scope_functions

import java.util.Locale

object PersonFunctions {
    // Let
    fun printPersonDetails(person: Person): String {
        val name = person.name.uppercase(Locale.getDefault()) // Convert name to uppercase
        val age = person.age // Get age
        return "Name: $name, Age: $age"
    }

    // Also
    fun logPersonCreation(person: Person?): Person? {
        if (person != null) {
            println("Person created: " + person.name)
        }
        return person
    }

    // Apply
    fun initializePerson(theName: String?, theAge: Int): Person {
        val person = Person()
        person.name = theName
        person.age = theAge
        return person
    }

    // Run
    fun getPersonDetails(person: Person?): String {
        if (person != null) {
            val details = StringBuilder()
            details.append("Name: ").append(person.name)
            details.append(", Age: ").append(person.age)
            return details.toString()
        }
        return "Unknown"
    }
}