package com.example.service

import com.example.entity.Customer
import com.example.repository.CustomerRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow

class CustomerServiceTest : ShouldSpec({
    val customersRepository: CustomerRepository = mockk()
    val customerService = CustomerService(customersRepository)

    context("getCustomers") {
        should("return customers for given city") {
            val customers = flow<Customer> {
                Customer(1, "John", 99, "Pune")
            }

            every { customersRepository.getCustomersFromCity("Pune") }.returns(customers)

            customerService.getCustomers("Pune") shouldBe customers

        }
    }
})
