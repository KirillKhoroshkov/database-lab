package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "account", schema = "events")
class Account(

        @Column(name = "username", columnDefinition = CITEXT)
        val username: String,

        @Column(name = "description")
        var description: String? = null,

        @Column(name = "email")
        val email: String,

        @Column(name = "is_active")
        var isActive: Boolean = false,

        @Column(name = "is_blocked")
        var isBlocked: Boolean = false,

        @Column(name = "registered_at")
        val registeredAt: Timestamp = Timestamp(System.currentTimeMillis())

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountAsPK.account")
    var password: Password? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountAsPK.account")
    var passwordToken: PasswordToken? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountAsPK.account")
    var verification: AccountVerification? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    val sessions: MutableSet<Session> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    val links: MutableSet<Link> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    val sentMessages: MutableSet<Message> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    val receivedMessages: MutableSet<Message> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    val incomingReports: MutableSet<AccountReport> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "organizer_blacklist",
            joinColumns = [JoinColumn(name = "organizer_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val blacklistedBy: MutableSet<Account> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "organizer_subscription",
            joinColumns = [JoinColumn(name = "organizer_id")],
            inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    val subscribers: MutableSet<Account> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "organizer_blacklist",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "organizer_id")]
    )
    val organizerBlacklist: MutableSet<Account> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizer")
    val organizedEvents: MutableSet<Event> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_event",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val savedEvents: MutableSet<Event> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "event_blacklist",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val eventBlacklist: MutableSet<Event> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    val eventComments: MutableSet<EventComment> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_event_date",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "event_date_id")]
    )
    val savedEventDates: MutableSet<EventDate> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    val eventReports: MutableSet<EventReport> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    val eventDateReports: MutableSet<EventDateReport> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "saved_location",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "location_id")]
    )
    val savedLocations: MutableSet<Location> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "tag_blacklist",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tagBlacklist: MutableSet<Tag> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
            schema = "events",
            name = "organizer_subscription",
            joinColumns = [JoinColumn(name = "account_id")],
            inverseJoinColumns = [JoinColumn(name = "organizer_id")]
    )
    val organizerSubscriptions: MutableSet<Account> = mutableSetOf()

    override fun getId(): Int? = _id
}