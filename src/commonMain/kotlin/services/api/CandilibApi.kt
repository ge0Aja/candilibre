package services.api

import services.api.client.buildClient
import services.api.model.BookingResult
import services.api.model.Centre
import services.api.model.Place

internal object CandilibApi {
    private fun httpClient(token: String) = buildClient(
        scheme = "https",
        appHost = "beta.interieur.gouv.fr/candilib",
        apiPath = "api/v2",
        appJWTToken = token
    )

    suspend fun getCentres(token: String, depNumber: String): List<Centre> =
        httpClient(token).get("candidat/centres", "departement" to depNumber)

    suspend fun getPlacesForCentre(token: String, departmentName: String, centreName: String): List<String> =
        httpClient(token).get("candidat/places", "geoDepartement" to departmentName, "nomCentre" to centreName)

    suspend fun bookPlace(token: String, place: Place): BookingResult =
        httpClient(token).patch("candidat/places", place)
}