package org.polykek.events.model

import javax.persistence.*

@Entity
@Table(name = "link", schema = "events")
class Link : AbstractEntity<Int>() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    var account: Account? = null

    @Column(name = "url")
    var URL: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_type_id")
    var type: LinkType? = null

}