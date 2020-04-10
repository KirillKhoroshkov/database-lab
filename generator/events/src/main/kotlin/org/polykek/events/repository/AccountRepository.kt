package org.polykek.events.repository

import org.polykek.events.model.Account

interface AccountRepository : FindingMaxIdRepository<Account, Int>