package org.polykek.events.repository

import org.polykek.events.model.Location

interface LocationRepository : FindingMaxIdRepository<Location, Int>