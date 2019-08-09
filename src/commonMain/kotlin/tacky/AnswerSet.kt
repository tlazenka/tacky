package tacky

data class AnswerSet(private val realizer: RealizerBase, private val variables: Set<String>) {
    fun next(): Map<String, Term>? = realizer.next()?.reified?.filter { variables.contains(it.key) }

    fun toList(): kotlin.collections.List<Map<String, Term>> {
        val result = mutableListOf<Map<String, Term>>()
        var next = next()
        while (next != null) {
            result.add(next)
            next = next()
        }
        return result
    }
}
