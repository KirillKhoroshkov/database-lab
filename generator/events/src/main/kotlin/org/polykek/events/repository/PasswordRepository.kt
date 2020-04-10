package org.polykek.events.repository

import org.polykek.events.model.AccountAsId
import org.polykek.events.model.Password
import org.springframework.data.repository.CrudRepository

interface PasswordRepository : CrudRepository<Password, AccountAsId>