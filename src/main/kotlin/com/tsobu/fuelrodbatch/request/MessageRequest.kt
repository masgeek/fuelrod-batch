package com.tsobu.fuelrodbatch.request

open class MessageRequest(
        var user: String,
        var password: String,
        var to: String,
        var text: String
) {

    var from: String? = null
    var token: String = "wUGcrypS0b1JhxJ9nA6E6f0JY6ORQ9Wb"
    var type: String = "longSMS"
    var queue = false
    var broadCastName: String? = "NA"
    var campaignId: Long? = null
}