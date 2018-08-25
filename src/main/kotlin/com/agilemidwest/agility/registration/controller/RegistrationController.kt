package com.agilemidwest.agility.registration.controller

import com.agilemidwest.agility.contract.RegistrationRequest
import com.agilemidwest.agility.contract.RegistrationResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/registration")
class RegistrationController {
    @RequestMapping(method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun register(@RequestBody registrationRequest: RegistrationRequest): RegistrationResponse {
        return RegistrationResponse(true, registrationRequest.sessionId, registrationRequest.attendeeId)
    }
}
