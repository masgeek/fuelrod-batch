package com.tsobu.fuelrodbatch.entities

import lombok.Data
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*


@Data
@Entity
@Table(name = "api_users")
class ApiUser : BaseEntity() {

    @Column(unique = true)
    var userName: String? = null

    var userEmail: String? = null

    var userPassword: String? = null

    @Column(name = "message_cost", columnDefinition = "decimal", precision = 10, scale = 2)
    var messageCost: Double = 0.0

    @Column(name = "sms_credits", columnDefinition = "decimal", precision = 10, scale = 2)
    var smsCredits: Double = 0.0

    @OneToMany(fetch = FetchType.LAZY, targetEntity = ApiUserServices::class, mappedBy = "apiUser", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    var userServices: List<ApiUserServices>? = null

    var active: Boolean? = false

    @Transient
    var validCredentials: Boolean? = false

    fun hasEnoughCredits(): Boolean {
        return this.smsCredits > 120
    }

    fun addUserApiServices(apiUser: ApiUser) {
        val apiUserServiceSet: ArrayList<ApiUserServices> = ArrayList<ApiUserServices>()

        apiUser.userServices!!.forEach { apiUserService ->
            apiUserService.apiUser = apiUser
            apiUserServiceSet.add(apiUserService)
        }
        this.userServices = apiUserServiceSet
    }
}