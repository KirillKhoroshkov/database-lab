package org.polykek.events.repository

import org.polykek.events.model.EventReport

interface EventReportRepository : FindingMaxIdRepository<EventReport, Int>