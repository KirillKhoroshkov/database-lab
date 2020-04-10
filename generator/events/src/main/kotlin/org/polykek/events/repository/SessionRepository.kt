package org.polykek.events.repository

import org.polykek.events.model.Session
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SessionRepository : CrudRepository<Session, UUID>