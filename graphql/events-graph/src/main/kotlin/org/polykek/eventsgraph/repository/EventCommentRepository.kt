package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.EventComment
import org.springframework.data.jpa.repository.JpaRepository

interface EventCommentRepository : JpaRepository<EventComment, Int>