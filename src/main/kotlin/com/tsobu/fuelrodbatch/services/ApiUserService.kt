package com.tsobu.fuelrodbatch.services


import com.tsobu.fuelrodbatch.entities.ApiUser
import com.tsobu.fuelrodbatch.entities.ApiUserServices
import com.tsobu.fuelrodbatch.repositories.ApiUserServicesRepository
import com.tsobu.fuelrodbatch.repositories.ApiUsersRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Suppress("DuplicatedCode")
@Service
class ApiUserService
constructor(val apiUsersRepository: ApiUsersRepository,
            val apiUserServicesRepository: ApiUserServicesRepository,
            @Autowired
            private val passwordEncoder: PasswordEncoder) {


    private val modelMapper = ModelMapper()


    fun getActiveService(userId: Long): ApiUserServices {
        val services = apiUserServicesRepository.findByUserIdAndActiveIsTrue(userId);
        return services;
    }

    fun getApiUser(username: String, password: String): ApiUser? {

        val apiUser = apiUsersRepository.findByUserNameAndActiveIsTrue(username)
        if (apiUser != null) {
            apiUser.validCredentials = passwordEncoder.matches(password, apiUser.userPassword)
        }
        return apiUser
    }
}