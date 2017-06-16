package me.mkweb.slacksvmeal.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author Mario Kunz
 */
class OffsetUtil {
    companion object {
        fun isValidOffset(offset: Int, start: LocalDate = LocalDate.now()): Boolean {
            if (offset > 4 || offset < 0) {
                return false
            }
            val day = start.dayOfWeek.value + offset
            if (day < DayOfWeek.SATURDAY.value) {
                return true
            }
            return false
        }

        fun getValidOffsets(start: LocalDate = LocalDate.now()): IntRange = IntRange(0, 1) //TODO use params

        fun getDateByOffset(offset: Int): LocalDate = LocalDate.now().plusDays(offset.toLong())

        fun getDateByOffsetString(offset: Int): String = getDateByOffset(offset).format(DateTimeFormatter.ofPattern("d MMM uuuu"))
    }
}