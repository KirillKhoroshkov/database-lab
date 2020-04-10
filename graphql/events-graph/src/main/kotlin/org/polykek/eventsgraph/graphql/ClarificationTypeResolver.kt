package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.ClarificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClarificationTypeResolver : GraphQLResolver<ClarificationType> {

    @Autowired
    private lateinit var clarificationRepository: ClarificationRepository

    fun getClarifications(clarificationType: ClarificationType): Set<Clarification> {
        return clarificationRepository.findAllByType(clarificationType)
    }
}