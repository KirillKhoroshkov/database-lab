package org.polykek.events.repository

import org.polykek.events.model.Link

interface LinkRepository : FindingMaxIdRepository<Link, Int>