package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class Query : GraphQLQueryResolver {

    @Autowired
    private lateinit var clarificationRepository: ClarificationRepository

    @Autowired
    private lateinit var clarificationTypeRepository: ClarificationTypeRepository

    @Autowired
    private lateinit var federalSubjectRepository: FederalSubjectRepository

    @Autowired
    private lateinit var federalSubjectTimezoneRepository: FederalSubjectTimezoneRepository

    @Autowired
    private lateinit var federalSubjectTypeRepository: FederalSubjectTypeRepository

    @Autowired
    private lateinit var localityRepository: LocalityRepository

    @Autowired
    private lateinit var localityTypeRepository: LocalityTypeRepository

    @Autowired
    private lateinit var locationRepository: LocationRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var placeTypeRepository: PlaceTypeRepository

    fun clarifications(page: Int, size: Int): List<Clarification> {
        return clarificationRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun clarification(id: Int): Clarification? {
        return clarificationRepository.findByIdOrNull(id)
    }

    fun clarificationTypes(page: Int, size: Int): List<ClarificationType> {
        return clarificationTypeRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun clarificationType(id: Short): ClarificationType? {
        return clarificationTypeRepository.findByIdOrNull(id)
    }

    fun federalSubjects(page: Int, size: Int): List<FederalSubject> {
        return federalSubjectRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun federalSubject(id: Short): FederalSubject? {
        return federalSubjectRepository.findByIdOrNull(id)
    }

    fun federalSubjectTimezones(page: Int, size: Int): List<FederalSubjectTimezone> {
        return federalSubjectTimezoneRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun federalSubjectTimezone(id: Short): FederalSubjectTimezone? {
        return federalSubjectTimezoneRepository.findByIdOrNull(id)
    }

    fun federalSubjectTypes(page: Int, size: Int): List<FederalSubjectType> {
        return federalSubjectTypeRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun federalSubjectType(id: Short): FederalSubjectType? {
        return federalSubjectTypeRepository.findByIdOrNull(id)
    }

    fun localities(page: Int, size: Int): List<Locality> {
        return localityRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun locality(id: Int): Locality? {
        return localityRepository.findByIdOrNull(id)
    }

    fun localityTypes(page: Int, size: Int): List<LocalityType> {
        return localityTypeRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun localityType(id: Short): LocalityType? {
        return localityTypeRepository.findByIdOrNull(id)
    }

    fun locations(page: Int, size: Int): List<Location> {
        return locationRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun location(id: Int): Location? {
        return locationRepository.findByIdOrNull(id)
    }

    fun places(page: Int, size: Int): List<Place> {
        return placeRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun place(id: Int): Place? {
        return placeRepository.findByIdOrNull(id)
    }

    fun placeTypes(page: Int, size: Int): List<PlaceType> {
        return placeTypeRepository.findAll(PageRequest.of(page, size)).toList()
    }

    fun placeType(id: Short): PlaceType? {
        return placeTypeRepository.findByIdOrNull(id)
    }
}