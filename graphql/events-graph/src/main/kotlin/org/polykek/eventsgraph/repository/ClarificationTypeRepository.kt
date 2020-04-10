package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.ClarificationType
import org.springframework.data.jpa.repository.JpaRepository

interface ClarificationTypeRepository : JpaRepository<ClarificationType, Short>