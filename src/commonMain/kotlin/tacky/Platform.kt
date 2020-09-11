package tacky

expect object Platform {
    fun assert(value: Boolean, message: String? = null)
}
