package org.polykek.events.repository

import org.polykek.events.model.EventComment

interface EventCommentRepository : FindingMaxIdRepository<EventComment, Int>