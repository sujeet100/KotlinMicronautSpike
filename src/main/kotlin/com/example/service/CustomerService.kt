package com.example.service

import com.example.entity.Customer
import com.example.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class CustomerService(private val customerRepository: CustomerRepository) {

    fun getCustomers(city: String): Flow<Customer> {
        return customerRepository.getCustomersFromCity(city)
    }

    suspend fun getCustomerById(id: Int): Customer {
        return customerRepository.getCustomerById(id)
    }
}
