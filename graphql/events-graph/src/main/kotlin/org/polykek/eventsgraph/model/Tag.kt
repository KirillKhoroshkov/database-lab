package org.polykek.eventsgraph.model

import org.polykek.eventsgraph.type.CITEXT
import javax.persistence.*

@Entity
@Table(name = "tag", schema = "events")
class Tag(

        @Column(name = "name", columnDefinition = CITEXT)
        val name: String

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

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

    override fun getId(): Int? = _id
}