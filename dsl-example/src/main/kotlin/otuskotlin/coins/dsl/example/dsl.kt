package otuskotlin.coins.dsl.example

import java.time.LocalDate
import java.time.Month

@UserDsl
class UserConfig {
    var id: UserId = UserId.NONE
    var name: Name = Name.NONE
    var birth: Birth = Birth.NONE
    var phone: Phone = Phone.NONE
    var email: Email = Email.NONE
    var permissions: MutableSet<Permission> = mutableSetOf()

    @UserDsl
    fun name(block: NameConfig.() -> Unit) {
        name = NameConfig().apply(block).createName()
    }

    @UserDsl
    fun contacts(block: ContactsConfig.() -> Unit) {
        val config = ContactsConfig().apply(block)
        phone = Phone(config.phone)
        email = Email(config.email)
    }

    @UserDsl
    fun permissions(block: PermissionConfig.() -> Unit) {
        val config = PermissionConfig().apply(block)
        permissions = config.permissions.toMutableSet()
    }

    @UserDsl
    fun birth(block: BirthConfig.() -> Unit) {
        val config = BirthConfig().apply(block)
        birth = Birth(config.date)
    }

    fun createUser() = User(id, name, birth, phone, email, permissions)
}

@UserDsl
class BirthConfig {
    var date: LocalDate = LocalDate.MIN
    infix fun Int.January(year: Int): LocalDate = LocalDate.of(year, Month.JANUARY, this)
}

@UserDsl
class PermissionConfig {

    private val _permissions: MutableSet<Permission> = mutableSetOf()
    val permissions: Set<Permission>
        get() = _permissions.toSet()

    operator fun Permission.unaryPlus() = _permissions.add(this)
}

@UserDsl
class ContactsConfig {
    var email: String = ""
    var phone: String = ""
}

@UserDsl
class NameConfig {
    var first: String = ""
    var second: String = ""
    var last: String = ""

    fun createName() = Name(first, second, last)
}

@UserDsl
fun user(block: UserConfig.() -> Unit) = UserConfig().apply(block).createUser()

@DslMarker
annotation class UserDsl