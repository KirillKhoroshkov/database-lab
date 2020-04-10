package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.FederalSubjectType
import org.springframework.data.jpa.repository.JpaRepository

interface FederalSubjectTypeRepository : JpaRepository<FederalSubjectType, Short>