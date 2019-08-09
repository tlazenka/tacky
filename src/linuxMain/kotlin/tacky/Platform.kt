package tacky

actual object Platform {
    actual fun assert(value: Boolean, message: String?) {
        when (message) {
            null -> kotlin.assert(value)
            else -> kotlin.assert(value) { message }
        }
    }
}