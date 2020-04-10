package org.polykek.events.repository

import org.polykek.events.model.AccountAsId
import org.polykek.events.model.PasswordToken
import org.springframework.data.repository.CrudRepository

interface PasswordTokenRepository : CrudRepository<PasswordToken, AccountAsId>