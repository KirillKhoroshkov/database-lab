package org.polykek.events.model

import org.hibernate.annotations.ColumnTransformer
import org.polykek.events.type.CITEXT
import org.polykek.events.type.EVENT_TYPE
import org.polykek.events.type.EventType
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event", schema = "events")
class Event : AbstractEntity<Int>() {

    @Column(name = "name", columnDefinition = CITEXT)
    var name: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "age_limit")
    var ageLimit: Short? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    var organizer: Account? = null

    @Column(name = "is_actual")
    var isActual: Boolean? = null

    @Column(name = "payment_service_url")
    var paymentServiceURL: String? = null

    @Column(name = "created_at")
    var createdAt: Timestamp? = null

    @Column(name = "is_blocked")
    var isBlocked: Boolean? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = EVENT_TYPE)
    @ColumnTransformer(read = "type::text", write = "?::$EVENT_TYPE")
    var type: EventType? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "event_tag",
            joinColumns = [JoinColumn(name = "event_id")],
            inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: MutableSet<Tag> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_event",
            joinColumns = [JoinColumn(name = "event_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val savedBy: MutableSet<Account> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    val dates: MutableSet<EventDate> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    val incomingReports: MutableSet<EventReport> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    val comments: MutableSet<EventComment> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "event_blacklist",
            joinColumns = [JoinColumn(name = "event_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val blacklistedBy: MutableSet<Account> = mutableSetOf()

}