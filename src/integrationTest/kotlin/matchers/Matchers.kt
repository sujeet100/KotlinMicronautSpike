package matchers

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult


fun matchJson(anotherValue: String, objectMapper: ObjectMapper) = object : Matcher<String> {
    override fun test(value: String): MatcherResult {
        return MatcherResult(
            objectMapper.readTree(value).toPrettyString() == objectMapper.readTree(anotherValue).toPrettyString(),
            "$value should be equal to $anotherValue",
            "$value should not be equal to $anotherValue"
        )
    }
}
