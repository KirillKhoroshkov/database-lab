package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Link
import org.springframework.data.jpa.repository.JpaRepository

interface LinkRepository : JpaRepository<Link, Int>