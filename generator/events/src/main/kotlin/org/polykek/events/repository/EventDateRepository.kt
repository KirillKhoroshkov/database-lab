package org.polykek.events.repository

import org.polykek.events.model.EventDate

interface EventDateRepository : FindingMaxIdRepository<EventDate, Int>