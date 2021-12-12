package otuskotlin.coins.dsl.example

import org.junit.Test
import otuskotlin.coins.dsl.example.Permission.*
import kotlin.test.assertEquals

internal class DslTest {

    @Test
    fun dslTest() {
        val user = user {
            name {
                first = "Denis"
                second = "Valerevich"
                last = "Pavlov"
            }

            contacts {
                email = "denispavlov1989@gmail.com"
                phone = "+79231582735"
            }

            birth {
                date = 25 January 1989
            }

            permissions {
                +GET_NEWS
                +ADD_COMMENTS
            }
        }

        println(user)
        assertEquals(user.name, Name(first = "Denis", second = "Valerevich", last = "Pavlov"))
    }
}