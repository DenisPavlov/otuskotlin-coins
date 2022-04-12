import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.Test

internal class VkAnalise {

    private val token = ""
    private val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Test
    fun test(): Unit = runBlocking {
        // val group = "aguru_pro"
        // val group = "numizmatika_of_russia"
        // val group = "perevoznikovcoins"
        // val group = "russkayamoneta"
        // val group = "monetamoscow"
        // val group = "brak_monet"
        // val group = "monety_rossii"
        val group = "i_sell_coins"

        val users = getAllUsers(group)

        println("User by sex:")
        val usersBySex = users.groupBy { it.sex }
        usersBySex.forEach {
            println("${it.key} : ${it.value.count()}")
        }

        println("Users ages:")
        users.mapNotNull {
            it.bdate
        }.mapNotNull {
            it.split(".").getOrNull(2)
        }.groupBy {
            it
        }.mapValues {
            it.value.count()
        }.toSortedMap().writeCsv(group, "age")

        println("Users by country")
        users.mapNotNull {
            it.country?.title
        }.groupBy {
            it
        }.mapValues {
            it.value.count()
        }.toSortedMap().writeCsv(group, "country")


        println("Users by city")
        users.mapNotNull {
            it.city?.title
        }.groupBy {
            it
        }.mapValues {
            it.value.count()
        }.toSortedMap().writeCsv(group, "city")

    }

    private fun Map<String, Int>.writeCsv(groupId: String, keyName: String, valueName: String = "count" ) {
        val writer = Files.newBufferedWriter(Paths.get("/home/denis/projects/otus/otuskotlin-coins/marketing/src/test/resources/${groupId}_$keyName.csv"))
        val printer = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(keyName, valueName))
        this.forEach {
            printer.printRecord(it.key, it.value)
        }
        printer.flush()
        printer.close()
    }

    private suspend fun getAllUsers(groupId: String): List<User> {
        val fields = listOf("bdate", "city", "country", "sex")

        val resp1: Response = client.get("https://api.vk.com/method/groups.getMembers") {
            parameter("group_id", groupId)
            parameter("fields", fields.joinToString(separator = ","))
            parameter("access_token", token)
            parameter("v", "5.131")
            parameter("count", 1000)
        }

        val users = mutableListOf(*resp1.response.items.toTypedArray())
        val amountOfUsers = resp1.response.count

        val range = 1000..amountOfUsers
        for (offset in range step 1000) {
            val resp2: Response = client.get("https://api.vk.com/method/groups.getMembers") {
                parameter("group_id", groupId)
                parameter("fields", fields.joinToString(separator = ","))
                parameter("access_token", token)
                parameter("v", "5.131")
                parameter("count", 1000)
                parameter("offset", offset)
            }
            users.addAll(resp2.response.items)
        }

        return users.toList()
    }
}

