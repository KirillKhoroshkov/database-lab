package org.polykek.events.service.generator

import org.polykek.events.model.*

interface Generator {

    fun printProperties()

    /** and organizer blacklist, and organizer subscriptions */
    fun generateAccounts(accountStartId: Int): Iterable<Account>

    fun generatePasswords(accounts: Iterable<Account>): Iterable<Password>

    fun generatePasswordTokens(accounts: Iterable<Account>): Iterable<PasswordToken>

    fun generateAccountVerifications(accounts: Iterable<Account>): Iterable<AccountVerification>

    fun generateAccountReports(accountReportStartId: Int, accounts: Iterable<Account>): Iterable<AccountReport>

    fun generateClarifications(clarificationStartId: Int, places: Iterable<Place>,
                               clarificationTypes: Iterable<ClarificationType>): Iterable<Clarification>

    fun generateClarificationTypes(clarificationTypeStartId: Short): Iterable<ClarificationType>

    fun generateEventComments(eventCommentStartId: Int, senders: Iterable<Account>,
                              events: Iterable<Event>): Iterable<EventComment>

    fun generateEventCosts(eventCostStartId: Long, eventDates: Iterable<EventDate>): Iterable<EventCost>

    fun generateEventDateReports(eventDateReportStartId: Int, eventDates: Iterable<EventDate>,
                                 senders: Iterable<Account>): Iterable<EventDateReport>

    /** @param accounts is who save event date */
    fun generateEventDates(eventDateStartId: Int, events: Iterable<Event>, locations: Iterable<Location>,
                           accounts: Iterable<Account>): Iterable<EventDate>

    fun generateEventReports(eventReportStartId: Int, events: Iterable<Event>,
                             senders: Iterable<Account>): Iterable<EventReport>

    /** @param accounts is organizers or who add event to blacklist or who save event */
    fun generateEvents(eventStartId: Int, accounts: Iterable<Account>,
                       tags: Iterable<Tag>): Iterable<Event>

    fun generateFederalSubjects(federalSubjectStartId: Short,
                                federalSubjectTypes: Iterable<FederalSubjectType>): Iterable<FederalSubject>

    fun generateFederalSubjectTimezones(federalSubjectTimezoneStartId: Short,
                                        federalSubjects: Iterable<FederalSubject>): Iterable<FederalSubjectTimezone>

    fun generateFederalSubjectTypes(federalSubjectTypeStartId: Short): Iterable<FederalSubjectType>

    fun generateLinks(linkStartId: Int, accounts: Iterable<Account>, linkTypes: Iterable<LinkType>): Iterable<Link>

    fun generateLinkTypes(linkTypeStartId: Short): Iterable<LinkType>

    fun generateLocalities(localityStartId: Int, localityTypes: Iterable<LocalityType>,
                           federalSubjectTimezones: Iterable<FederalSubjectTimezone>): Iterable<Locality>

    fun generateLocalityTypes(localityTypeStartId: Short): Iterable<LocalityType>

    /** @param accounts is who save location */
    fun generateLocations(locationStartId: Int, places: Iterable<Place>,
                          clarifications: Iterable<Clarification>,
                          accounts: Iterable<Account>): Iterable<Location>

    /** @param accounts is senders and recipients */
    fun generateMessages(messageStartId: Int, accounts: Iterable<Account>, events: Iterable<Event>,
                         eventDates: Iterable<EventDate>): Iterable<Message>

    fun generatePlaces(placeStartId: Int, placeTypes: Iterable<PlaceType>,
                       localities: Iterable<Locality>): Iterable<Place>

    fun generatePlaceTypes(placeTypeStartId: Short): Iterable<PlaceType>

    fun generateSessions(accounts: Iterable<Account>): Iterable<Session>

    /** @param accounts is who add tag to blacklist */
    fun generateTags(tagStartId: Int, accounts: Iterable<Account>): Iterable<Tag>

}