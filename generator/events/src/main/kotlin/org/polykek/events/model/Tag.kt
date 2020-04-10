package org.polykek.events.model

import org.polykek.events.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "tag", schema = "events")
class Tag : AbstractEntity<Int>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "event_tag",
            joinColumns = [JoinColumn(name = "tag_id")],
            inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val events: MutableSet<Event> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "tag_blacklist",
            joinColumns = [JoinColumn(name = "tag_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val blacklistedBy: MutableSet<Account> = mutableSetOf()
}