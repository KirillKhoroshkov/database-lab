package org.polykek.events.repository

import org.polykek.events.model.Event

interface EventRepository : FindingMaxIdRepository<Event, Int>