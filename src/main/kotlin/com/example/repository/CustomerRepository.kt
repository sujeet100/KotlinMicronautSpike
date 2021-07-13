package com.example.repository

import com.aerospike.client.exp.Exp
import com.aerospike.client.policy.QueryPolicy
import com.aerospike.client.query.Filter
import com.aerospike.client.query.Statement
import com.example.entity.Customer
import com.example.repository.ReactorClient.reactorClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import javax.inject.Singleton

@Singleton
class CustomerRepository {
    fun getCustomersFromCity(city: String): Flow<Customer> {
        val stmt = Statement().apply {
            namespace = "test"
            setName = "customers"
        }

        val queryPolicy = QueryPolicy().apply {
            filterExp = Exp.build(
                Exp.or(
                    Exp.gt(Exp.intBin("age"), Exp.`val`(20)),
                    Exp.eq(Exp.stringBin("address"), Exp.`val`(city))
                )
            )
        }

        return reactorClient.query(queryPolicy, stmt).asFlow().map {
            Customer(
                it.record.getInt("id"),
                it.record.getString("name"),
                it.record.getInt("age"),
                it.record.getString("address")
            )
        }
    }

    suspend fun getCustomerById(id: Int): Customer {
        val stmt = Statement().apply {
            namespace = "test"
            setName = "customers"
            filter = Filter.equal("PK", id.toLong())
        }
        val customer = reactorClient.query(stmt).awaitFirst()
        return Customer(
            customer.record.getInt("PK"),
            customer.record.getString("name"),
            customer.record.getInt("age"),
            customer.record.getString("address")
        )
    }
}
