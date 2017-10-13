package org.neo4j.cloudfoundry.odb.adapter

import org.neo4j.driver.v1.exceptions.Neo4jException

typealias CommandError = Pair<Int, (String) -> String>

object ErrorMessages {
    val deserializationFailed = { name: String -> "Parameter '$name' cannot be deserialized" }
    val missingMandatoryFields = { name: String, missing: List<String> -> "Parameter '$name' is missing mandatory parameters: ${missing.joinToString(", ")}" }
    val bindingAlreadyExists = { bindingId: String -> "Binding '$bindingId' already exists" }
    val userCreationFailed = { bindingId: String -> "User creation for binding '$bindingId' failed." }
    val noVmIps = { bindingId: String -> "No VM IP provided for binding '$bindingId'" }
    val noPassword = { bindingId: String -> "No Neo4j password provided for binding '$bindingId'" }
    val unreachableVM = { bindingId: String, e: Neo4jException -> "Could not reach VM for binding '$bindingId'.\n" +
        "Details: ${e.message}" }
}

object CommandErrors {
    val bindingAlreadyExistsError = CommandError(49, ErrorMessages.bindingAlreadyExists)
    val userCreationError = CommandError(99, ErrorMessages.userCreationFailed)
    val noVmIpError = CommandError(199, ErrorMessages.noVmIps)
    val noPasswordError = CommandError(299, ErrorMessages.noPassword)
    val unreachableVmError = Pair(79, ErrorMessages.unreachableVM)
}