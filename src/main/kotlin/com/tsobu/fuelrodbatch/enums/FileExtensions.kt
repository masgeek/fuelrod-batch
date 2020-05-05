package com.tsobu.fuelrod.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class FileExtensions {
    @JsonProperty("xls")
    xls,

    @JsonProperty("xlsx")
    xlsx,
}
