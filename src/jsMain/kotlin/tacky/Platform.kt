package tacky

actual object Platform {
    actual fun assert(value: Boolean, message: String?) {
        if (!(value)) {
            throw AssertionError(message ?: "Assertion failed")
        }
    }
}
