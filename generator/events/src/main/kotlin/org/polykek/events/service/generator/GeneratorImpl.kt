package org.polykek.events.service.generator

import org.polykek.events.model.*
import org.polykek.events.type.EventType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.sql.Timestamp
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

@Service
class GeneratorImpl : Generator {

    @Autowired
    lateinit var generatorProperties: GeneratorProperties

    val startTime: Long by lazy { Timestamp.valueOf(generatorProperties.startTime).time }
    val currentTime: Long by lazy { System.currentTimeMillis() }

    val random = Random.Default

    override fun printProperties() {
        println(generatorProperties)
    }

    /** and organizer blacklist, and organizer subscriptions */
    override fun generateAccounts(accountStartId: Int): Iterable<Account> {
        val accounts = mutableListOf<Account>()
        val timeDifference = currentTime - startTime + 1
        for (i in 0 until generatorProperties.accountsNumber) {
            val account = Account()
            account._id = accountStartId + i
            account.username = "account" + account.id
            account.description = "description of " + account.username
            account.email = account.username + "@example.com"
            account.isActive = true
            account.isBlocked = false
            val registrationTime = startTime + random.nextLong(timeDifference).absoluteValue + 1
            account.registeredAt = Timestamp(registrationTime)
            accounts.add(account)
        }
        accounts.sortBy { it.registeredAt }
        val previouslyAddedAccounts = mutableListOf<Account>()
        for (account in accounts) {
            val subscriptionsNumber = random.nextInt(generatorProperties.maxAccountSubscriptionsNumber).absoluteValue + 1
            val subscriptions = previouslyAddedAccounts.shuffled().take(subscriptionsNumber)
            account.organizerSubscriptions.addAll(subscriptions)
            val blacklistedNumber = random.nextInt(generatorProperties.maxAccountBlacklistNumber).absoluteValue + 1
            val blacklisted = previouslyAddedAccounts.shuffled().take(blacklistedNumber)
            account.organizerSubscriptions.addAll(blacklisted)
            previouslyAddedAccounts.add(account)
        }
        return accounts
    }

    override fun generatePasswords(accounts: Iterable<Account>): Iterable<Password> {
        val passwords = mutableListOf<Password>()
        val digest = MessageDigest.getInstance("SHA-256")
        for (account in accounts) {
            val password = Password()
            password.salt = UUID.randomUUID()
            val text = "password" + account.id + password.salt
            val hash = digest.digest(text.toByteArray(StandardCharsets.UTF_8))
            password.hash = hash
            password.pk = AccountAsId(account)
            passwords.add(password)
        }
        return passwords
    }

    override fun generatePasswordTokens(accounts: Iterable<Account>): Iterable<PasswordToken> {
        val passwordTokens = mutableListOf<PasswordToken>()
        for (account in accounts) {
            val passwordToken = PasswordToken()
            passwordToken.code = UUID.randomUUID()
            val registrationTime = account.registeredAt!!.time
            val difference = currentTime - registrationTime
            val expiresIn = startTime + random.nextLong(difference).absoluteValue + 1
            passwordToken.expiresIn = Timestamp(expiresIn)
            passwordToken.pk = AccountAsId(account)
            passwordTokens.add(passwordToken)
        }
        return passwordTokens
    }

    override fun generateAccountVerifications(accounts: Iterable<Account>): Iterable<AccountVerification> {
        val shuffledAccounts = accounts.shuffled()
        val verificationNumber = random.nextInt(shuffledAccounts.size).absoluteValue + 1
        val verifiedAccounts = shuffledAccounts.take(verificationNumber)
        val verifications = mutableListOf<AccountVerification>()
        for (account in verifiedAccounts) {
            val verification = AccountVerification()
            verification.fullName = "Verified account with id:" + account.id
            val registrationTime = account.registeredAt!!.time
            val difference = currentTime - registrationTime
            val verifiedAt = startTime + random.nextLong(difference).absoluteValue + 1
            verification.verifiedAt = Timestamp(verifiedAt)
            verification.pk = AccountAsId(account)
            verifications.add(verification)
        }
        return verifications
    }

    override fun generateAccountReports(accountReportStartId: Int, accounts: Iterable<Account>): Iterable<AccountReport> {
        val shuffledAccounts = accounts.shuffled()
        val reportedAccountsNumber = (shuffledAccounts.size * generatorProperties.reportedAccountsPercent / 100)
        val reportedAccounts = shuffledAccounts.take(reportedAccountsNumber)
        val accountReports = mutableListOf<AccountReport>()
        var currentId = accountReportStartId
        for (reported in reportedAccounts) {
            val reportersNumber = random.nextInt(generatorProperties.maxAccountReportNumber).absoluteValue + 1
            val reporters = accounts
                    .filter { it != reported }
                    .shuffled()
                    .take(reportersNumber)
            for (reporter in reporters) {
                val accountReport = AccountReport()
                accountReport._id = currentId
                accountReport.account = reported
                accountReport.sender = reporter
                val startTime = (if (reporter.registeredAt!! < reported.registeredAt!!) reported.registeredAt!! else reporter.registeredAt!!).time
                val difference = currentTime - startTime
                accountReport.sentAt = Timestamp(startTime + random.nextLong(difference).absoluteValue + 1)
                accountReport.message = "Report to account with id:" + reported.id
                accountReports.add(accountReport)
                currentId++
            }
        }
        return accountReports
    }

    override fun generateClarifications(clarificationStartId: Int, places: Iterable<Place>, clarificationTypes: Iterable<ClarificationType>): Iterable<Clarification> {
        var currentId = clarificationStartId
        val clarificationTypeList = clarificationTypes.toList()
        val clarifications = mutableListOf<Clarification>()
        for (place in places) {
            val clarificationNumber = random.nextInt(generatorProperties.maxClarificationNumberForPlace).absoluteValue + 1
            for (i in 0 until clarificationNumber) {
                val clarification = Clarification()
                val clarificationType = clarificationTypeList.random()
                clarification._id = currentId
                clarification.type = clarificationType
                clarification.place = place
                clarification.value = "clarification " + clarification.id
                clarifications.add(clarification)
                currentId++
            }
        }
        return clarifications
    }

    override fun generateClarificationTypes(clarificationTypeStartId: Short): Iterable<ClarificationType> {
        val types = mutableListOf<ClarificationType>()
        for (i in 0 until generatorProperties.clarificationTypesNumber) {
            val clarificationType = ClarificationType()
            clarificationType._id = (clarificationTypeStartId + i).toShort()
            clarificationType.name = "clarification type " + clarificationType.id
            clarificationType.abbreviation = "type " + clarificationType.id
            clarificationType.outputOrder = i.toShort()
            types.add(clarificationType)
        }
        return types
    }

    override fun generateEventComments(eventCommentStartId: Int, senders: Iterable<Account>, events: Iterable<Event>): Iterable<EventComment> {
        var currentId = eventCommentStartId
        val eventComments = mutableListOf<EventComment>()
        for (event in events) {
            val number = random.nextInt(generatorProperties.maxCommentsNumberOfEvent).absoluteValue + 1
            val senderList = senders
                    .filter { it.registeredAt!! < event.createdAt!! }
                    .filter { it != event.organizer }
                    .shuffled()
                    .take(number)
            for (sender in senderList) {
                val eventComment = EventComment()
                eventComment._id = currentId
                eventComment.sender = sender
                eventComment.message = "Comment with id " + eventComment.id + " from account with id " +
                        eventComment.sender!!.id
                eventComment.event = event
                val startTime = event.createdAt!!.time
                val difference = currentTime - startTime
                val sentAt = startTime + random.nextLong(difference).absoluteValue + 1
                eventComment.sentAt = Timestamp(sentAt)
                eventComments.add(eventComment)
                currentId++
            }
        }
        return eventComments
    }

    override fun generateEventCosts(eventCostStartId: Long, eventDates: Iterable<EventDate>): Iterable<EventCost> {
        var currentId = eventCostStartId
        val eventCosts = mutableListOf<EventCost>()
        for (eventDate in eventDates) {
            val number = random.nextInt(generatorProperties.maxEventCostNumber).absoluteValue + 1
            for (i in 0 until number) {
                val eventCost = EventCost()
                eventCost._id = currentId
                eventCost.cost = random.nextInt(generatorProperties.maxEventCost).absoluteValue + 1
                eventCost.isActual = true
                eventCost.eventDate = eventDate
                eventCost.description = "cost: " + eventCost.cost
                eventCost.createdAt = eventDate.createdAt
                eventCosts.add(eventCost)
                currentId++
            }
        }
        return eventCosts
    }

    override fun generateEventDateReports(eventDateReportStartId: Int, eventDates: Iterable<EventDate>, senders: Iterable<Account>): Iterable<EventDateReport> {
        val shuffledEventDates = eventDates.shuffled()
        val reportedEventDatesNumber = (shuffledEventDates.size * generatorProperties.reportedEventDatesPercent / 100)
        val reportedEventDates = shuffledEventDates.take(reportedEventDatesNumber)
        val eventDateReports = mutableListOf<EventDateReport>()
        var currentId = eventDateReportStartId
        for (eventDate in reportedEventDates) {
            val reportersNumber = random.nextInt(generatorProperties.maxEventDateReportsNumber).absoluteValue + 1
            val reporters = senders
                    .filter { it.registeredAt!! < eventDate.createdAt!! }
                    .filter { it != eventDate.event?.organizer }
                    .shuffled()
                    .take(reportersNumber)
            for (reporter in reporters) {
                val eventDateReport = EventDateReport()
                eventDateReport._id = currentId
                eventDateReport.eventDate = eventDate
                eventDateReport.sender = reporter
                eventDateReport.message = "Report to event date with id:" + eventDate.id
                val startTime = eventDate.createdAt!!.time
                val difference = currentTime - startTime
                val sentAt = startTime + random.nextLong(difference).absoluteValue + 1
                eventDateReport.sentAt = Timestamp(sentAt)
                eventDateReports.add(eventDateReport)
                currentId++
            }
        }
        return eventDateReports
    }

    /** @param accounts is who save event date */
    override fun generateEventDates(eventDateStartId: Int, events: Iterable<Event>, locations: Iterable<Location>, accounts: Iterable<Account>): Iterable<EventDate> {
        var currentId = eventDateStartId
        val eventDates = mutableListOf<EventDate>()
        for (event in events) {
            val organizerLocations = locations.filter { it.savedBy.contains(event.organizer) }
            val eventDatesNumber = random.nextInt(generatorProperties.maxEventDatesNumber).absoluteValue + 1
            for (i in 0 until eventDatesNumber) {
                val eventDate = EventDate()
                eventDate._id = currentId
                eventDate.event = event
                eventDate.description = "Event date with id:" + eventDate.id + " of event with id: " + event.id
                val differenceForCreatedAt = currentTime - event.createdAt!!.time
                val createdAt = event.createdAt!!.time + random.nextLong(differenceForCreatedAt).absoluteValue + 1
                eventDate.createdAt = Timestamp(createdAt)
                val differenceForStartDate = currentTime - createdAt
                val startDate = createdAt + random.nextLong(differenceForStartDate).absoluteValue + 1
                eventDate.startDate = Timestamp(startDate)
                val differenceForEndDate = generatorProperties.maxEventDateDurationByDays * 86400000L
                val endDate = startDate + random.nextLong(differenceForEndDate).absoluteValue + 1
                eventDate.endDate = Timestamp(endDate)
                eventDate.isActual = true
                eventDate.isBlocked = false
                eventDate.location = organizerLocations.random()
                val savedBy = accounts
                        .filter { it.registeredAt!! < eventDate.createdAt!! }
                        .shuffled()
                        .take(random.nextInt(generatorProperties.maxAccountSavedEventDateNumber).absoluteValue + 1)
                eventDate.savedBy.addAll(savedBy)
                eventDates.add(eventDate)
                currentId++
            }
        }
        return eventDates
    }

    override fun generateEventReports(eventReportStartId: Int, events: Iterable<Event>, senders: Iterable<Account>): Iterable<EventReport> {
        val shuffledEvents = events.shuffled()
        val reportedEventsNumber = (shuffledEvents.size * generatorProperties.reportedEventsPercent / 100)
        val reportedEvents = shuffledEvents.take(reportedEventsNumber)
        val eventReports = mutableListOf<EventReport>()
        var currentId = eventReportStartId
        for (event in reportedEvents) {
            val reportersNumber = random.nextInt(generatorProperties.maxEventReportsNumber).absoluteValue + 1
            val reporters = senders
                    .filter { it.registeredAt!! < event.createdAt!! }
                    .filter { it != event.organizer }
                    .shuffled()
                    .take(reportersNumber)
            for (reporter in reporters) {
                val eventReport = EventReport()
                eventReport._id = currentId
                eventReport.event = event
                eventReport.sender = reporter
                eventReport.message = "Report to event with id:" + event.id
                val startTime = event.createdAt!!.time
                val difference = currentTime - startTime
                val sentAt = startTime + random.nextLong(difference).absoluteValue + 1
                eventReport.sentAt = Timestamp(sentAt)
                eventReports.add(eventReport)
                currentId++
            }
        }
        return eventReports
    }

    /** @param accounts is organizers or who add event to blacklist or who save event */
    override fun generateEvents(eventStartId: Int, accounts: Iterable<Account>, tags: Iterable<Tag>): Iterable<Event> {
        val accountList = accounts
                .shuffled()
        val organizers = accountList
                .take(accountList.size * generatorProperties.organizersPercent / 100)
        val events = mutableListOf<Event>()
        for (i in 0 until generatorProperties.eventsNumber) {
            val event = Event()
            event._id = eventStartId + i
            event.ageLimit = (random.nextInt(18).absoluteValue + 1).toShort()
            event.name = "Event with id: " + event.id
            val organizer = organizers.random()
            event.organizer = organizer
            event.description = "Event with id: " + event.id + "organized by account with id: " + organizer.id
            event.isActual = true
            event.paymentServiceURL = "https://example.com/payment/" + event.id
            val difference = currentTime - organizer.registeredAt!!.time
            event.createdAt = Timestamp(organizer.registeredAt!!.time + random.nextLong(difference).absoluteValue + 1)
            event.isBlocked = false
            val type = when (random.nextInt(3).absoluteValue) {
                0 -> EventType.event
                1 -> EventType.schedule
                2 -> EventType.service
                else -> EventType.event
            }
            event.type = type
            val eventTags = tags
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxEventTagsNumber).absoluteValue + 1)
            event.tags.addAll(eventTags)
            val savedBy = accountList
                    .filter { it.registeredAt!! < event.createdAt!! }
                    .filter { it != event.organizer }
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxSavedEventAccountsNumber).absoluteValue + 1)
            event.savedBy.addAll(savedBy)
            val blacklistedBy = accountList
                    .filter { it.registeredAt!! < event.createdAt!! }
                    .filter { it != event.organizer }
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxBlacklistedEventAccountsNumber).absoluteValue + 1)
            event.blacklistedBy.addAll(blacklistedBy)
            events.add(event)
        }
        return events
    }

    override fun generateFederalSubjects(federalSubjectStartId: Short, federalSubjectTypes: Iterable<FederalSubjectType>): Iterable<FederalSubject> {
        val federalSubjects = mutableListOf<FederalSubject>()
        val types = federalSubjectTypes.toList()
        for (i in 0 until generatorProperties.federalSubjectsNumber) {
            val federalSubject = FederalSubject()
            federalSubject._id = (federalSubjectStartId + i).toShort()
            federalSubject.name = "Federal Subject with id: " + federalSubject.id
            federalSubject.type = types.random()
            federalSubjects.add(federalSubject)
        }
        return federalSubjects
    }

    override fun generateFederalSubjectTimezones(federalSubjectTimezoneStartId: Short, federalSubjects: Iterable<FederalSubject>): Iterable<FederalSubjectTimezone> {
        var currentId = federalSubjectTimezoneStartId
        val federalSubjectTimezones = mutableListOf<FederalSubjectTimezone>()
        for (federalSubject in federalSubjects) {
            val number = random.nextInt(generatorProperties.maxTimezonesNumberForFederalSubject) + 1
            for (i in 0 until number) {
                val federalSubjectTimezone = FederalSubjectTimezone()
                federalSubjectTimezone._id = currentId
                federalSubjectTimezone.federalSubject = federalSubject
                federalSubjectTimezone.utcOffset = random.nextInt(12)
                federalSubjectTimezones.add(federalSubjectTimezone)
                currentId++
            }
        }
        return federalSubjectTimezones
    }

    override fun generateFederalSubjectTypes(federalSubjectTypeStartId: Short): Iterable<FederalSubjectType> {
        val federalSubjectTypes = mutableListOf<FederalSubjectType>()
        for (i in 0 until generatorProperties.federalSubjectTypesNumber) {
            val federalSubjectType = FederalSubjectType()
            federalSubjectType._id = (federalSubjectTypeStartId + i).toShort()
            federalSubjectType.name = "Federal subject type with id: " + federalSubjectType.id
            federalSubjectType.abbreviation = "Type with id: " + federalSubjectType.id
            federalSubjectTypes.add(federalSubjectType)
        }
        return federalSubjectTypes
    }

    override fun generateLinks(linkStartId: Int, accounts: Iterable<Account>, linkTypes: Iterable<LinkType>): Iterable<Link> {
        var currentId = linkStartId
        val links = mutableListOf<Link>()
        for (linkType in linkTypes) {
            for (account in accounts) {
                val link = Link()
                link._id = currentId
                link.account = account
                link.type = linkType
                link.URL = "https://example.com/" + linkType.name + "/account/" + account.username
                links.add(link)
                currentId++
            }
        }
        return links
    }

    override fun generateLinkTypes(linkTypeStartId: Short): Iterable<LinkType> {
        val linkTypes = mutableListOf<LinkType>()
        for (i in 0 until generatorProperties.linkTypesNumber) {
            val linkType = LinkType()
            linkType._id = (linkTypeStartId + i).toShort()
            linkType.name = "link" + linkType.id
            linkTypes.add(linkType)
        }
        return linkTypes
    }

    override fun generateLocalities(localityStartId: Int, localityTypes: Iterable<LocalityType>, federalSubjectTimezones: Iterable<FederalSubjectTimezone>): Iterable<Locality> {
        val types = localityTypes.toList()
        val subjectTimezones = federalSubjectTimezones.toList()
        val localities = mutableListOf<Locality>()
        for (i in 0 until generatorProperties.localitiesNumber) {
            val locality = Locality()
            locality._id = localityStartId + i
            locality.name = "Locality with id: " + locality.id
            locality.type = types.random()
            locality.federalSubjectTimezone = subjectTimezones.random()
            localities.add(locality)
        }
        return localities
    }

    override fun generateLocalityTypes(localityTypeStartId: Short): Iterable<LocalityType> {
        val localityTypes = mutableListOf<LocalityType>()
        for (i in 0 until generatorProperties.localityTypesNumber) {
            val localityType = LocalityType()
            localityType._id = (localityTypeStartId + i).toShort()
            localityType.name = "Locality type with id: " + localityType.id
            localityType.abbreviation = "Type with id: " + localityType.id
            localityTypes.add(localityType)
        }
        return localityTypes
    }

    /** @param accounts is who save location */
    override fun generateLocations(locationStartId: Int, places: Iterable<Place>, clarifications: Iterable<Clarification>, accounts: Iterable<Account>): Iterable<Location> {
        val locations = mutableListOf<Location>()
        val localityPlaces = places.toList()
        for (i in 0 until generatorProperties.locationsNumber) {
            val location = Location()
            location._id = locationStartId + i
            location.mapURL = "https://example.com/location/" + location.id
            location.name = "Location with id: " + location.id
            location.place = localityPlaces.random()
            val placeClarifications = clarifications
                    .filter { it.place == location.place }
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxClarificationNumberForLocation).absoluteValue + 1)
            location.clarifications.addAll(placeClarifications)
            locations.add(location)
        }
        for (account in accounts) {
            for (i in 0 until random.nextInt(generatorProperties.maxSavedLocationsNumber).absoluteValue + 1) {
                val location = locations.random()
                location.savedBy.add(account)
            }
        }
        return locations
    }

    /** @param accounts is senders and recipients */
    override fun generateMessages(messageStartId: Int, accounts: Iterable<Account>, events: Iterable<Event>, eventDates: Iterable<EventDate>): Iterable<Message> {
        var currentId = messageStartId
        val messages = mutableListOf<Message>()
        for (event in events) {
            val senders = accounts
                    .filter { it.registeredAt!! < event.createdAt!! }
                    .filter { it != event.organizer }
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxMessageNumberForOrganizer).absoluteValue + 1)
            for (sender in senders) {
                val message = Message()
                message._id = currentId
                message.event = event
                message.recipient = event.organizer
                message.sender = senders.random()
                message.message = "Message from " + message.sender + " to " + message.recipient
                val startTime = event.createdAt!!.time
                val difference = currentTime - startTime
                val sentAt = startTime + random.nextLong(difference).absoluteValue + 1
                message.sentAt = Timestamp(sentAt)
                messages.add(message)
                currentId++
            }
        }
        return messages
    }

    override fun generatePlaces(placeStartId: Int, placeTypes: Iterable<PlaceType>, localities: Iterable<Locality>): Iterable<Place> {
        var currentId = placeStartId
        val types = placeTypes.toList()
        val places = mutableListOf<Place>()
        for (locality in localities) {
            for (i in 0 until random.nextInt(generatorProperties.maxPlacesNumberForLocality).absoluteValue + 1) {
                val place = Place()
                place._id = currentId
                place.name = "Place with id: " + place.id
                place.type = types.random()
                place.locality = locality
                places.add(place)
                currentId++
            }
        }
        return places
    }

    override fun generatePlaceTypes(placeTypeStartId: Short): Iterable<PlaceType> {
        val placeTypes = mutableListOf<PlaceType>()
        for (i in 0 until generatorProperties.placeTypesNumber) {
            val placeType = PlaceType()
            placeType._id = (placeTypeStartId + i).toShort()
            placeType.name = "Place type with id: " + placeType.id
            placeType.abbreviation = "Type with id: " + placeType.id
            placeTypes.add(placeType)
        }
        return placeTypes
    }

    override fun generateSessions(accounts: Iterable<Account>): Iterable<Session> {
        val sessions = mutableListOf<Session>()
        for (account in accounts) {
            for (i in 0 until random.nextInt(generatorProperties.maxSessionsNumberForUser).absoluteValue + 1) {
                val session = Session()
                session.accessToken = UUID.randomUUID()
                session.refreshToken = UUID.randomUUID()
                session.createdAt = Timestamp(currentTime)
                session.expiresIn = Timestamp(currentTime + 2 * 86400000L)
                session.account = account
                sessions.add(session)
            }
        }
        return sessions
    }

    /** @param accounts is who add tag to blacklist */
    override fun generateTags(tagStartId: Int, accounts: Iterable<Account>): Iterable<Tag> {
        val tags = mutableListOf<Tag>()
        for (i in 0 until generatorProperties.tagsNumber) {
            val tag = Tag()
            tag._id = tagStartId + i
            tag.name = "Tag with id: " + tag.id
            val blacklistedBy = accounts
                    .shuffled()
                    .take(random.nextInt(generatorProperties.maxBlacklistedTagAccountsNumber).absoluteValue + 1)
            tag.blacklistedBy.addAll(blacklistedBy)
            tags.add(tag)
        }
        return tags
    }
}