package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.AccountAsPK
import org.polykek.eventsgraph.model.PasswordToken
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordTokenRepository : JpaRepository<PasswordToken, AccountAsPK>