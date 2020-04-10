package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.FederalSubjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FederalSubjectTypeResolver : GraphQLResolver<FederalSubjectType> {

    @Autowired
    private lateinit var federalSubjectRepository: FederalSubjectRepository

    fun getFederalSubjects(federalSubjectType: FederalSubjectType): Set<FederalSubject> {
        return federalSubjectRepository.findAllByType(federalSubjectType)
    }
}