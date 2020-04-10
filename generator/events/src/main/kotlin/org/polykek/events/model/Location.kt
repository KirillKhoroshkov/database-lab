package org.polykek.events.model

import javax.persistence.*

@Entity
@Table(name = "location", schema = "events")
class Location : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: Place? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "map_url")
    var mapURL: String? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "location_clarification",
            joinColumns = [JoinColumn(name = "location_id")],
            inverseJoinColumns = [JoinColumn(name = "clarification_id")]
    )
    val clarifications: MutableSet<Clarification> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    val eventDates: MutableSet<EventDate> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_location",
            joinColumns = [JoinColumn(name = "location_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val savedBy: MutableSet<Account> = mutableSetOf()
}