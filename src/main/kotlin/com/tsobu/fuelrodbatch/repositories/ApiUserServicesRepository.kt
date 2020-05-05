package com.tsobu.fuelrodbatch.repositories


import com.tsobu.fuelrodbatch.entities.ApiUserServices
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApiUserServicesRepository : JpaRepository<ApiUserServices, Long> {


    override fun findById(id: Long): Optional<ApiUserServices>

    fun findAllByUserId(userId: Long): List<ApiUserServices>

    fun findByUserIdAndActiveIsTrue(userId: Long): ApiUserServices

    fun findAllByUserIdAndActiveIsTrue(userId: Long): List<ApiUserServices>
}