package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.AccountAsPK
import org.polykek.eventsgraph.model.Password
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordRepository : JpaRepository<Password, AccountAsPK>