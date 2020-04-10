package org.polykek.eventsgraph.model

import javax.persistence.*

@Entity
@Table(name = "link", schema = "events")
class Link(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        val account: Account,

        @Column(name = "url")
        val URL: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "link_type_id")
        val type: LinkType

) : AbstractPersistableEntity<Int>() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private var _id: Int? = null

    override fun getId(): Int? = _id
}