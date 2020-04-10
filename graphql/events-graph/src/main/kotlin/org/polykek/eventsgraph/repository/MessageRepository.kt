package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Int>