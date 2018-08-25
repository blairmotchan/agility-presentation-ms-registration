package com.agilemidwest.agility.contract

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(@JsonProperty("registered")
                                val registered: Boolean,
                                @JsonProperty("sessionId")
                                val sessionId: Long,
                                @JsonProperty("attendeeId")
                                val attendeeId: Long)