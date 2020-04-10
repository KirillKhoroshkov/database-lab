package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Int>