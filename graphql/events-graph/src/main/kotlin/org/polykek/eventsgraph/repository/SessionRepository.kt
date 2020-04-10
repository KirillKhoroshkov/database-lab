package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Session
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SessionRepository : JpaRepository<Session, UUID>