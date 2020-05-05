package com.tsobu.fuelrodbatch.request


open class ApiUserRequest(
        var userName: String,

        var userEmail: String,
        var userPassword: String,

        var smsCredits: Double,
        var userServices: List<ApiUserServiceRequest>
) {
    var sender: String? = null
    var active: Boolean? = false
}