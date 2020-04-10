package org.polykek.eventsgraph.repository

import org.polykek.eventsgraph.model.AccountReport
import org.springframework.data.jpa.repository.JpaRepository

interface AccountReportRepository : JpaRepository<AccountReport, Int>