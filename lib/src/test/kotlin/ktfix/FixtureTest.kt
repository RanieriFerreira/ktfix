package ktfix

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.time.LocalDateTime

data class BasicTypes(
    val string: String,
    val char: Char,
    val double: Double,
    val float: Float,
    val int: Int,
    val short: Short,
    val long: Long,
    val byte: Byte,
    val boolean: Boolean
)

enum class EnumClazz {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH
}

data class Clazz(val double: Double, val string: String, val int: Int)
data class ClazzWithClass(val integer: Int, val clazz: Clazz)
data class ClazzWithDate(val localDateTime: LocalDateTime, val localDate: LocalDate)
data class ClazzWithEnum(val enumClass: EnumClazz)

class FixtureTest {

    @Test
    fun `should build a fixture of a class with only primitive types`() {
        assertDoesNotThrow { Fixture.build<BasicTypes>() }
    }

    @Test
    fun `should build a fixture of a class with another class`() {
        assertDoesNotThrow { Fixture.build<ClazzWithClass>() }
    }

    @Test
    fun `should build a fixture with date and local date properties`() {
        assertDoesNotThrow { Fixture.build<ClazzWithDate>() }
    }

    @Test
    fun `should build a fixture with enum properties`() {
        assertDoesNotThrow {
            val value = Fixture.build<ClazzWithEnum>()
            assert(value.enumClass in EnumClazz.values())
        }
    }

    @Test
    fun `should build a fixture with predefined values`() {
        val defaults = mutableMapOf<String, Any>(
            ("string" to "cool string"),
            ("char" to 'h'),
            ("double" to 0.123),
            ("float" to 0.123f),
            ("int" to 2),
            ("byte" to 1),
            ("short" to 2.toShort()),
            ("long" to 1L),
            ("boolean" to false)
        )

        val basic = Fixture.build<BasicTypes>(defaults)

        assert(basic.string == "cool string")
        assert(basic.char == 'h')
        assert(basic.double == 0.123)
        assert(basic.float == 0.123f)
        assert(basic.int == 2)
        assert(basic.byte == 1.toByte())
        assert(basic.short == 2.toShort())
        assert(basic.long == 1L)
        assert(!basic.boolean)
    }
}
