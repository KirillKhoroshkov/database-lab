package org.polykek.events.service.generator

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

@Configuration
@ConfigurationProperties(prefix = "generator")
@Validated
class GeneratorProperties {

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")
    lateinit var startTime: String

    @Min(0)
    var maxAccountSubscriptionsNumber: Int = 0

    @Min(0)
    var maxAccountBlacklistNumber: Int = 0

    @Min(0)
    var accountsNumber: Int = 0

    @Min(0)
    var maxAccountReportNumber: Int = 0

    @Min(0)
    @Max(100)
    var reportedAccountsPercent: Int = 0

    @Min(0)
    var clarificationTypesNumber: Int = 0

    @Min(0)
    var maxClarificationNumberForPlace: Int = 0

    @Min(0)
    var maxCommentsNumberOfEvent: Int = 0

    @Min(0)
    var maxEventCostNumber: Int = 0

    @Min(0)
    var maxEventCost: Int = 0

    @Min(0)
    @Max(100)
    var reportedEventDatesPercent: Int = 0

    @Min(0)
    var maxEventDateReportsNumber: Int = 0

    @Min(0)
    var maxEventDatesNumber: Int = 0

    @Min(1)
    var maxEventDateDurationByDays: Int = 1

    @Min(0)
    var maxAccountSavedEventDateNumber: Int = 1

    @Min(0)
    @Max(100)
    var reportedEventsPercent: Int = 0

    @Min(0)
    var maxEventReportsNumber: Int = 0

    @Min(0)
    @Max(100)
    var organizersPercent: Int = 0

    @Min(0)
    var eventsNumber: Int = 0

    @Min(0)
    var maxEventTagsNumber: Int = 0

    @Min(0)
    var maxBlacklistedEventAccountsNumber: Int = 0

    @Min(0)
    var maxSavedEventAccountsNumber: Int = 0

    @Min(0)
    var federalSubjectsNumber: Int = 0

    @Min(0)
    var maxTimezonesNumberForFederalSubject: Int = 0

    @Min(0)
    var federalSubjectTypesNumber: Int = 0

    @Min(0)
    var linkTypesNumber: Int = 0

    @Min(0)
    var localitiesNumber: Int = 0

    @Min(0)
    var localityTypesNumber: Int = 0

    @Min(0)
    var locationsNumber: Int = 0

    @Min(0)
    var maxClarificationNumberForLocation: Int = 0

    @Min(0)
    var maxSavedLocationsNumber: Int = 0

    @Min(0)
    var maxMessageNumberForOrganizer: Int = 0

    @Min(0)
    var maxPlacesNumberForLocality: Int = 0

    @Min(0)
    var placeTypesNumber: Int = 0

    @Min(0)
    var maxSessionsNumberForUser: Int = 0

    @Min(0)
    var tagsNumber: Int = 0

    @Min(0)
    var maxBlacklistedTagAccountsNumber: Int = 0

    override fun toString(): String {
        return "startTime: " + startTime + "\n" +
        "accountsNumber: " + accountsNumber + "\n" +
        "maxAccountSubscriptionsNumber: " + maxAccountSubscriptionsNumber + "\n" +
        "maxAccountBlacklistNumber: " + maxAccountBlacklistNumber + "\n" +
        "maxAccountReportNumber: " + maxAccountReportNumber + "\n" +
        "reportedAccountsPercent: " + reportedAccountsPercent + "\n" +
        "clarificationTypesNumber: " + clarificationTypesNumber + "\n" +
        "maxClarificationNumberForPlace: " + maxClarificationNumberForPlace + "\n" +
        "maxCommentsNumberOfEvent: " + maxCommentsNumberOfEvent + "\n" +
        "maxEventCostNumber: " + maxEventCostNumber + "\n" +
        "maxEventCost: " + maxEventCost + "\n" +
        "reportedEventDatesPercent: " + reportedEventDatesPercent + "\n" +
        "maxEventDateReportsNumber: " + maxEventDateReportsNumber + "\n" +
        "maxEventDatesNumber: " + maxEventDatesNumber + "\n" +
        "maxEventDateDurationByDays: " + maxEventDateDurationByDays + "\n" +
        "maxAccountSavedEventDateNumber: " + maxAccountSavedEventDateNumber + "\n" +
        "reportedEventsPercent: " + reportedEventsPercent + "\n" +
        "maxEventReportsNumber: " + maxEventReportsNumber + "\n" +
        "organizersPercent: " + organizersPercent + "\n" +
        "eventsNumber: " + eventsNumber + "\n" +
        "maxEventTagsNumber: " + maxEventTagsNumber + "\n" +
        "maxBlacklistedEventAccountsNumber: " + maxBlacklistedEventAccountsNumber + "\n" +
        "maxSavedEventAccountsNumber: " + maxSavedEventAccountsNumber + "\n" +
        "federalSubjectsNumber: " + federalSubjectsNumber + "\n" +
        "maxTimezonesNumberForFederalSubject: " + maxTimezonesNumberForFederalSubject + "\n" +
        "federalSubjectTypesNumber: " + federalSubjectTypesNumber + "\n" +
        "linkTypesNumber: " + linkTypesNumber + "\n" +
        "localitiesNumber: " + localitiesNumber + "\n" +
        "localityTypesNumber: " + localityTypesNumber + "\n" +
        "locationsNumber: " + locationsNumber + "\n" +
        "maxClarificationNumberForLocation: " + maxClarificationNumberForLocation + "\n" +
        "maxSavedLocationsNumber: " + maxSavedLocationsNumber + "\n" +
        "maxMessageNumberForOrganizer: " + maxMessageNumberForOrganizer + "\n" +
        "maxPlacesNumberForLocality: " + maxPlacesNumberForLocality + "\n" +
        "placeTypesNumber: " + placeTypesNumber + "\n" +
        "maxSessionsNumberForUser: " + maxSessionsNumberForUser + "\n" +
        "tagsNumber: " + tagsNumber + "\n" +
        "maxBlacklistedTagAccountsNumber: " + maxBlacklistedTagAccountsNumber
    }
}