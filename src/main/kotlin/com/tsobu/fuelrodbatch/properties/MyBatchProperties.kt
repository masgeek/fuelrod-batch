package com.tsobu.fuelrodbatch.properties


import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import javax.validation.constraints.NotBlank

@Data
class MyBatchProperties {

    @NotBlank
    var cron: String? = null


}
