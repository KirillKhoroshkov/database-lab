package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.LinkType
import org.springframework.data.jpa.repository.JpaRepository

interface LinkTypeRepository : JpaRepository<LinkType, Short>