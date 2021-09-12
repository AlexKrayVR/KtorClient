package alex.com.ktorclient

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TestClass(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("age") val age: String
)