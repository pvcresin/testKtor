import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            static("/") {
                staticRootFolder = File("static")
                default("index.html")
                files("./")
            }
            get("/json") {
                call.respond(Item(1, "alpha"))
            }
        }
        install(ContentNegotiation) {
            jackson {
                configure(SerializationFeature.INDENT_OUTPUT, true)
            }
        }
    }.start(wait = true)
}

data class Item(val id: Int, val name: String)