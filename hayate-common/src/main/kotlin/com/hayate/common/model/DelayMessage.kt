package com.hayate.common.model

import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

/**
 * Created by Flame on 2020/06/09.
 */

class DelayMessage(
        val key: String,
        val value: Any? = null,
        val availableTime: Long = 5000L
) : Delayed {

    override fun getDelay(unit: TimeUnit): Long {
        return unit.convert(availableTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }

    override fun compareTo(o: Delayed): Int {
        return getDelay(TimeUnit.MILLISECONDS).compareTo(o.getDelay(TimeUnit.MILLISECONDS))
    }
}