package org.polykek.events.repository

import org.polykek.events.model.AbstractEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface FindingMaxIdRepository<T: AbstractEntity<ID>, ID: Serializable> : CrudRepository<T, ID> {

    @Query("select max(_id) from #{#entityName}")
    fun findMaxId(): ID?
}