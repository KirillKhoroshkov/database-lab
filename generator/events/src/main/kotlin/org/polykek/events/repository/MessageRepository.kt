package org.polykek.events.repository

import org.polykek.events.model.Message

interface MessageRepository : FindingMaxIdRepository<Message, Int>