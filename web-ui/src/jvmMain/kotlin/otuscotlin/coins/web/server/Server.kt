package otuscotlin.coins.web.server

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val config = HoconApplicationConfig(ConfigFactory.load())
val port = config.property("ktor.deployment.port").getString().toInt()

fun main() {
    embeddedServer(Netty, port) {
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html,
                )
            }
            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}
