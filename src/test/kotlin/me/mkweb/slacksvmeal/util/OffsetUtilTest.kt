package me.mkweb.slacksvmeal.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

/**
 * @author Mario Kunz
 */
class OffsetUtilTest {

    companion object {
        val MOCKED_DATE: LocalDate = LocalDate.of(2017, 6, 6) // Tuesday
    }

    @Test
    fun isValidOffset_validOffset_returnsTrue() {
        assertThat(OffsetUtil.isValidOffset(3, MOCKED_DATE)).isTrue()
    }

    @Test
    fun isValidOffset_negativeOffset_returnsFalse() {
        assertThat(OffsetUtil.isValidOffset(-3, MOCKED_DATE)).isFalse()
    }

    @Test
    fun isValidOffset_offsetToHigh_returnsFalse() {
        assertThat(OffsetUtil.isValidOffset(100, MOCKED_DATE)).isFalse()
    }

    @Test
    fun isValidOffset_offsetOnNextWeekend_returnsFalse() {
        assertThat(OffsetUtil.isValidOffset(4, MOCKED_DATE)).isFalse()
    }

    @Test
    fun isValidOffset_todaysOffset_returnsTrue() {
        assertThat(OffsetUtil.isValidOffset(0, MOCKED_DATE)).isTrue()
    }
}