package com.example.repository

import com.aerospike.client.AerospikeClient
import com.aerospike.client.async.NioEventLoops
import com.aerospike.client.policy.ClientPolicy
import com.aerospike.client.reactor.AerospikeReactorClient

object ReactorClient {
    val reactorClient by lazy {
        val policy = ClientPolicy()
        policy.eventLoops = NioEventLoops(1)
        val client = AerospikeClient(policy, "localhost", 3000)
        AerospikeReactorClient(client)
    }

}
