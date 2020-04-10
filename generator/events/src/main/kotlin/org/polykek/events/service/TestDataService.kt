package org.polykek.events.service

import org.polykek.events.repository.*
import org.polykek.events.service.generator.Generator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TestDataService {

    @Autowired
    private lateinit var accountReportRepository: AccountReportRepository

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var accountVerificationRepository: AccountVerificationRepository

    @Autowired
    private lateinit var clarificationRepository: ClarificationRepository

    @Autowired
    private lateinit var clarificationTypeRepository: ClarificationTypeRepository

    @Autowired
    private lateinit var eventCommentRepository: EventCommentRepository

    @Autowired
    private lateinit var eventCostRepository: EventCostRepository

    @Autowired
    private lateinit var eventDateReportRepository: EventDateReportRepository

    @Autowired
    private lateinit var eventDateRepository: EventDateRepository

    @Autowired
    private lateinit var eventReportRepository: EventReportRepository

    @Autowired
    private lateinit var eventRepository: EventRepository

    @Autowired
    private lateinit var federalSubjectRepository: FederalSubjectRepository

    @Autowired
    private lateinit var federalSubjectTimezoneRepository: FederalSubjectTimezoneRepository

    @Autowired
    private lateinit var federalSubjectTypeRepository: FederalSubjectTypeRepository

    @Autowired
    private lateinit var linkRepository: LinkRepository

    @Autowired
    private lateinit var linkTypeRepository: LinkTypeRepository

    @Autowired
    private lateinit var localityRepository: LocalityRepository

    @Autowired
    private lateinit var localityTypeRepository: LocalityTypeRepository

    @Autowired
    private lateinit var locationRepository: LocationRepository

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Autowired
    private lateinit var passwordRepository: PasswordRepository

    @Autowired
    private lateinit var passwordTokenRepository: PasswordTokenRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var placeTypeRepository: PlaceTypeRepository

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Autowired
    private lateinit var generator: Generator

    @Transactional
    fun generateTestData() {

        generator.printProperties()

        val accountStartId = (accountRepository.findMaxId() ?: 0) + 1
        val accounts = generator.generateAccounts(accountStartId)
        accountRepository.saveAll(accounts)

        val passwords = generator.generatePasswords(accounts)
        passwordRepository.saveAll(passwords)

        val passwordTokens = generator.generatePasswordTokens(accounts)
        passwordTokenRepository.saveAll(passwordTokens)

        val accountVerifications = generator.generateAccountVerifications(accounts)
        accountVerificationRepository.saveAll(accountVerifications)

        val accountReportStartId = (accountReportRepository.findMaxId() ?: 0) + 1
        val accountReports = generator.generateAccountReports(accountReportStartId, accounts)
        accountReportRepository.saveAll(accountReports)

        val clarificationTypeStartId = ((clarificationTypeRepository.findMaxId() ?: 0) + 1).toShort()
        val clarificationTypes = generator.generateClarificationTypes(clarificationTypeStartId)
        clarificationTypeRepository.saveAll(clarificationTypes)

        val tagStartId = (tagRepository.findMaxId() ?: 0) + 1
        val tags = generator.generateTags(tagStartId, accounts)
        tagRepository.saveAll(tags)

        val eventStartId = (eventRepository.findMaxId() ?: 0) + 1
        val events = generator.generateEvents(eventStartId, accounts, tags)
        eventRepository.saveAll(events)

        val eventReportStartId = (eventReportRepository.findMaxId() ?: 0) + 1
        val eventReports = generator.generateEventReports(eventReportStartId, events, accounts)
        eventReportRepository.saveAll(eventReports)

        val eventCommentStartId = (eventCommentRepository.findMaxId() ?: 0) + 1
        val eventComments = generator.generateEventComments(eventCommentStartId, accounts, events)
        eventCommentRepository.saveAll(eventComments)

        val federalSubjectTypeStartId = ((federalSubjectTypeRepository.findMaxId() ?: 0) + 1).toShort()
        val federalSubjectTypes = generator.generateFederalSubjectTypes(federalSubjectTypeStartId)
        federalSubjectTypeRepository.saveAll(federalSubjectTypes)

        val federalSubjectStartId = ((federalSubjectRepository.findMaxId() ?: 0) + 1).toShort()
        val federalSubjects = generator.generateFederalSubjects(federalSubjectStartId, federalSubjectTypes)
        federalSubjectRepository.saveAll(federalSubjects)

        val federalSubjectTimezoneStartId = ((federalSubjectTimezoneRepository.findMaxId() ?: 0) + 1).toShort()
        val federalSubjectTimezones = generator
                .generateFederalSubjectTimezones(federalSubjectTimezoneStartId, federalSubjects)
        federalSubjectTimezoneRepository.saveAll(federalSubjectTimezones)

        val linkTypeStartId = ((linkTypeRepository.findMaxId() ?: 0) + 1).toShort()
        val linkTypes = generator.generateLinkTypes(linkTypeStartId)
        linkTypeRepository.saveAll(linkTypes)

        val linkStartId = (linkRepository.findMaxId() ?: 0) + 1
        val links = generator.generateLinks(linkStartId, accounts, linkTypes)
        linkRepository.saveAll(links)

        val localityTypeStartId = ((localityTypeRepository.findMaxId() ?: 0) + 1).toShort()
        val localityTypes = generator.generateLocalityTypes(localityTypeStartId)
        localityTypeRepository.saveAll(localityTypes)

        val localityStartId = (localityRepository.findMaxId() ?: 0) + 1
        val localities = generator.generateLocalities(localityStartId, localityTypes, federalSubjectTimezones)
        localityRepository.saveAll(localities)

        val placeTypeStartId = ((placeTypeRepository.findMaxId() ?: 0) + 1).toShort()
        val placeTypes = generator.generatePlaceTypes(placeTypeStartId)
        placeTypeRepository.saveAll(placeTypes)

        val placeStartId = (placeRepository.findMaxId() ?: 0) + 1
        val places = generator.generatePlaces(placeStartId, placeTypes, localities)
        placeRepository.saveAll(places)

        val clarificationStartId = (clarificationRepository.findMaxId() ?: 0) + 1
        val clarifications = generator.generateClarifications(clarificationStartId, places, clarificationTypes)
        clarificationRepository.saveAll(clarifications)

        val locationStartId = (locationRepository.findMaxId() ?: 0) + 1
        val locations = generator.generateLocations(locationStartId, places, clarifications, accounts)
        locationRepository.saveAll(locations)

        val eventDateStartId = (eventDateRepository.findMaxId() ?: 0) + 1
        val eventDates = generator.generateEventDates(eventDateStartId, events ,locations, accounts)
        eventDateRepository.saveAll(eventDates)

        val eventDateReportStartId = (eventDateReportRepository.findMaxId() ?: 0) + 1
        val eventDateReports = generator.generateEventDateReports(eventDateReportStartId, eventDates, accounts)
        eventDateReportRepository.saveAll(eventDateReports)

        val eventCostStartId = (eventCostRepository.findMaxId() ?: 0) + 1
        val eventCosts = generator.generateEventCosts(eventCostStartId, eventDates)
        eventCostRepository.saveAll(eventCosts)

        val messageStartId = (messageRepository.findMaxId() ?: 0) + 1
        val messages = generator.generateMessages(messageStartId, accounts, events, eventDates)
        messageRepository.saveAll(messages)

        val sessions = generator.generateSessions(accounts)
        sessionRepository.saveAll(sessions)

    }

}