package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.FederalSubjectTimezoneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FederalSubjectResolver : GraphQLResolver<FederalSubject> {

    @Autowired
    private lateinit var federalSubjectTimezoneRepository: FederalSubjectTimezoneRepository

    fun getType(federalSubject: FederalSubject): FederalSubjectType {
        return federalSubject.type
    }

    fun getTimezones(federalSubject: FederalSubject): Set<FederalSubjectTimezone> {
        return federalSubjectTimezoneRepository.findAllByFederalSubject(federalSubject)
    }
}