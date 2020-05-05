package com.tsobu.fuelrodbatch.entities

import com.tsobu.fuelrodbatch.enums.EnumService
import javax.persistence.*


@Entity
@Table(name = "api_user_services")
class ApiUserServices : BaseEntity() {

    @Column(name = "user_id", insertable = false, updatable = false)
    var userId: Long? = null

    @Enumerated(EnumType.STRING)
    var msgService: EnumService? = null

    var serviceUser: String? = null

    var servicePassword: String? = null

    @Column(name = "sender_id")
    var sender: String? = null

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ApiUser::class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var apiUser: ApiUser? = null

    var active: Boolean? = false
}