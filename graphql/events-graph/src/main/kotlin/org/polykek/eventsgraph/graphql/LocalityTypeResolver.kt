package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.LocalityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocalityTypeResolver : GraphQLResolver<LocalityType> {

    @Autowired
    private lateinit var localityRepository: LocalityRepository

    fun getLocalities(localityType: LocalityType): Set<Locality> {
        return localityRepository.findAllByType(localityType)
    }
}