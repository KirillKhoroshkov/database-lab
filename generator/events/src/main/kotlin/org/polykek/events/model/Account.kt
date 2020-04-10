package org.polykek.events.model

import org.polykek.events.type.CITEXT
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "account", schema = "events")
class Account : AbstractEntity<Int>() {

    @Column(name = "username", columnDefinition = CITEXT)
    var username: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "is_active")
    var isActive: Boolean? = null

    @Column(name = "is_blocked")
    var isBlocked: Boolean? = null

    @Column(name = "registered_at")
    var registeredAt: Timestamp? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pk.account")
    var password: Password? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pk.account")
    var passwordToken: PasswordToken? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pk.account")
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

}