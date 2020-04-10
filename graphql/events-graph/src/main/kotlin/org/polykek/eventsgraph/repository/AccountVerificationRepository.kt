package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.AccountAsPK
import org.polykek.eventsgraph.model.AccountVerification
import org.springframework.data.jpa.repository.JpaRepository

interface AccountVerificationRepository : JpaRepository<AccountVerification, AccountAsPK>