package com.tsobu.fuelrodbatch.repositories

import com.tsobu.fuelrodbatch.entities.ApiUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface ApiUsersRepository : JpaRepository<ApiUser, Long> {

    @Transactional
    override fun findById(id: Long): Optional<ApiUser>

    fun findByIdAndUserName(id: Long, username: String): ApiUser?

    override fun findAll(): List<ApiUser>

    fun findByUserName(username: String): ApiUser?

    fun findByUserNameAndActiveIsTrue(username: String): ApiUser?

    fun findByUserNameAndUserPasswordAndActiveIsTrue(username: String, password: String): ApiUser?
}