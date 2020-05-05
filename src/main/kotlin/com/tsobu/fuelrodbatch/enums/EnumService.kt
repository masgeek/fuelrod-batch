package com.tsobu.fuelrodbatch.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class EnumService {
    @JsonProperty("INFOBIP")
    INFOBIP,

    @JsonProperty("AFRICAT")
    AFRICAT,

    @JsonProperty("MOVESMS")
    MOVESMS
}
