package org.polykek.events.repository

import org.polykek.events.model.Tag

interface TagRepository : FindingMaxIdRepository<Tag, Int>