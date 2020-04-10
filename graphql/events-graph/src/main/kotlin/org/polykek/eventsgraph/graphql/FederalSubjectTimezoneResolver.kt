package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.LocalityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FederalSubjectTimezoneResolver : GraphQLResolver<FederalSubjectTimezone> {

    @Autowired
    private lateinit var localityRepository: LocalityRepository

    fun getFederalSubject(federalSubjectTimezone: FederalSubjectTimezone): FederalSubject {
        return federalSubjectTimezone.federalSubject
    }

    fun getLocalities(federalSubjectTimezone: FederalSubjectTimezone): Set<Locality> {
        return localityRepository.findAllByFederalSubjectTimezone(federalSubjectTimezone)
    }
}