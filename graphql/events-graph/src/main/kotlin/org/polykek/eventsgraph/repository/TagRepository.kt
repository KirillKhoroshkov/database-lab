package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Int>