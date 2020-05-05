package com.tsobu.fuelrodbatch.request

import com.tsobu.fuelrodbatch.enums.EnumService
import lombok.Data


@Data
class ApiUserServiceRequest(
        var msgService: EnumService,
        var serviceUser: String,
        var servicePassword: String
) {
    var sender: String? = null

    var active: Boolean? = false

}