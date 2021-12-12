package otuskotlin.coins.dsl.example

import java.time.LocalDate

data class User(
    val id: UserId = UserId.NONE,
    val name: Name = Name.NONE,
    val birth: Birth = Birth.NONE,
    val phone: Phone = Phone.NONE,
    val email: Email = Email.NONE,
    val permissions: Set<Permission> = setOf(),
)

@JvmInline
value class UserId(val id: String) {
    companion object {
        val NONE: UserId = UserId("")
    }
}

@JvmInline
value class Phone(val phone: String = "") {
    companion object{
        val NONE = Phone()
    }
}

@JvmInline
value class Email(val email: String = "") {
    companion object{
        val NONE = Email()
    }
}

data class Birth(val date: LocalDate = LocalDate.MIN) {
    companion object {
        val NONE = Birth()
    }
}

data class Name(
    val first: String = "",
    val second: String = "",
    val last: String = "",
) {
    companion object {
        val NONE = Name()
    }
}

enum class Permission {
    GET_NEWS,
    ADD_COMMENTS,
}
