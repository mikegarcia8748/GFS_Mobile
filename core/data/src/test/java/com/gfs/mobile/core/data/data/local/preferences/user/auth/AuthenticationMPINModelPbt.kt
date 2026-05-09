package com.gfs.mobile.core.data.data.local.preferences.user.auth

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.checkAll
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthenticationMPINModelPbt : StringSpec({

    val json = Json { ignoreUnknownKeys = true }

    "AuthenticationMPINModel should survive serialization round-trip" {
        // PBT-02: Round-Trip Properties
        // PBT-07: Generator Quality
        val modelArb = arbitrary {
            AuthenticationMPINModel(
                accessToken = Arb.string().orNull().bind(),
                accountType = Arb.string().orNull().bind(),
                fullName = Arb.string().orNull().bind(),
                userID = Arb.string().orNull().bind(),
                userName = Arb.string().orNull().bind()
            )
        }

        checkAll(modelArb) { model ->
            val encoded = json.encodeToString(model)
            val decoded = json.decodeFromString<AuthenticationMPINModel>(encoded)
            decoded shouldBe model
        }
    }
})
