package org.polykek.eventsgraph.graphql

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.polykek.eventsgraph.model.*
import org.polykek.eventsgraph.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class Mutation : GraphQLMutationResolver {

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

    fun newClarification(placeId: Int, typeId: Short, value: String): Clarification? {
        val place = placeRepository.findByIdOrNull(placeId) ?: return null
        val type = clarificationTypeRepository.findByIdOrNull(typeId) ?: return null
        return try {
            val clarification = Clarification(place, type, value)
            clarificationRepository.save(clarification)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteClarification(id: Int): Boolean {
        return try {
            clarificationRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newClarificationType(name: String, abbreviation: String, outputOrder: Short): ClarificationType? {
        return try {
            val clarificationType = ClarificationType(name, abbreviation, outputOrder)
            clarificationTypeRepository.save(clarificationType)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteClarificationType(id: Short): Boolean {
        return try {
            clarificationTypeRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newFederalSubject(name: String, typeId: Short): FederalSubject? {
        val type = federalSubjectTypeRepository.findByIdOrNull(typeId) ?: return null
        return try {
            val federalSubject = FederalSubject(name, type)
            federalSubjectRepository.save(federalSubject)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteFederalSubject(id: Short): Boolean {
        return try {
            federalSubjectRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newFederalSubjectTimezone(federalSubjectId: Short, utcOffset: Int): FederalSubjectTimezone? {
        val federalSubject = federalSubjectRepository.findByIdOrNull(federalSubjectId) ?: return null
        return try {
            val federalSubjectTimezone = FederalSubjectTimezone(federalSubject, utcOffset)
            federalSubjectTimezoneRepository.save(federalSubjectTimezone)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteFederalSubjectTimezone(id: Short): Boolean {
        return try {
            federalSubjectTimezoneRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newFederalSubjectType(name: String, abbreviation: String): FederalSubjectType? {
        return try {
            val federalSubjectType = FederalSubjectType(name, abbreviation)
            federalSubjectTypeRepository.save(federalSubjectType)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteFederalSubjectType(id: Short): Boolean {
        return try {
            federalSubjectTypeRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newLocality(name: String, typeId: Short, federalSubjectTimezoneId: Short): Locality? {
        val type = localityTypeRepository.findByIdOrNull(typeId) ?: return null
        val federalSubjectTimezone = federalSubjectTimezoneRepository.findByIdOrNull(federalSubjectTimezoneId)
                ?: return null
        return try {
            val locality = Locality(name, type, federalSubjectTimezone)
            localityRepository.save(locality)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteLocality(id: Int): Boolean {
        return try {
            localityRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun setLocalityName(id: Int, name: String): Locality? {
        val locality = localityRepository.findByIdOrNull(id) ?: return null
        locality.name = name
        return try {
            localityRepository.save(locality)
        } catch (ex: Exception) {
            null
        }
    }

    fun setLocalityType(id: Int, typeId: Short): Locality? {
        val locality = localityRepository.findByIdOrNull(id) ?: return null
        val type = localityTypeRepository.findByIdOrNull(typeId) ?: return null
        locality.type = type
        return try {
            localityRepository.save(locality)
        } catch (ex: Exception) {
            null
        }
    }

    fun setLocalityFederalSubjectTimezone(id: Int, federalSubjectTimezoneId: Short): Locality? {
        val locality = localityRepository.findByIdOrNull(id) ?: return null
        val federalSubjectTimezone = federalSubjectTimezoneRepository.findByIdOrNull(federalSubjectTimezoneId)
                ?: return null
        locality.federalSubjectTimezone = federalSubjectTimezone
        return try {
            localityRepository.save(locality)
        } catch (ex: Exception) {
            null
        }
    }

    fun newLocalityType(name: String, abbreviation: String): LocalityType? {
        return try {
            val localityType = LocalityType(name, abbreviation)
            localityTypeRepository.save(localityType)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteLocalityType(id: Short): Boolean {
        return try {
            localityTypeRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newLocation(placeId: Int, name: String?, mapURL: String?): Location? {
        val place = placeRepository.findByIdOrNull(placeId) ?: return null
        return try {
            val location = Location(place, name, mapURL)
            locationRepository.save(location)
        } catch (ex: Exception) {
            null
        }
    }

    fun deleteLocation(id: Int): Boolean {
        return try {
            locationRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newPlace(name: String, typeId: Short, localityId: Int): Place? {
        val type = placeTypeRepository.findByIdOrNull(typeId) ?:return null
        val locality = localityRepository.findByIdOrNull(localityId) ?: return null
        return try {
            val place = Place(name, type, locality)
            placeRepository.save(place)
        } catch (ex: Exception) {
            null
        }
    }

    fun deletePlace(id: Int): Boolean {
        return try {
            placeRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun newPlaceType(name: String, abbreviation: String): PlaceType? {
        return try {
            val placeType = PlaceType(name, abbreviation)
            placeTypeRepository.save(placeType)
        } catch (ex: Exception) {
            null
        }
    }

    fun deletePlaceType(id: Short): Boolean {
        return try {
            placeTypeRepository.deleteById(id)
            true
        } catch (ex: Exception) {
            false
        }
    }
}