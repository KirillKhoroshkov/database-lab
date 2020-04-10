package org.polykek.events.repository

import org.polykek.events.model.AccountAsId
import org.polykek.events.model.AccountVerification
import org.springframework.data.repository.CrudRepository

interface AccountVerificationRepository : CrudRepository<AccountVerification, AccountAsId>