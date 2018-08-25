package com.agilemidwest.agility.contract

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationRequest(
        @JsonProperty("attendeeId")
        val attendeeId: Long,
        @JsonProperty("sessionId")
        val sessionId: Long)