package org.polykek.eventsgraph.model

import org.hibernate.annotations.ColumnTransformer
import org.polykek.eventsgraph.type.CITEXT
import org.polykek.eventsgraph.type.EVENT_TYPE
import org.polykek.eventsgraph.type.EventType
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "event", schema = "events")
class Event(

        @Column(name = "name", columnDefinition = CITEXT)
        var name: String,

        @Column(name = "description")
        var description: String,

        @Column(name = "age_limit")
        var ageLimit: Short,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "organizer_id")
        val organizer: Account,

        @Column(name = "is_actual")
        var isActual: Boolean = true,

        @Column(name = "payment_service_url")
        var paymentServiceURL: String? = null,

        @Column(name = "created_at")
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

        @Column(name = "is_blocked")
        var isBlocked: Boolean = false,

        @Enumerated(EnumType.STRING)
        @Column(name = "type", columnDefinition = EVENT_TYPE)
        @ColumnTransformer(read = "type::text", write = "?::$EVENT_TYPE")
        val type: EventType

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

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

    override fun getId(): Int? = _id
}