package me.mkweb.slacksvmeal.util

import java.time.DayOfWeek
import java.time.LocalDate

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

        fun getValidOffsets(start: LocalDate = LocalDate.now()) {
            
        }
    }
}