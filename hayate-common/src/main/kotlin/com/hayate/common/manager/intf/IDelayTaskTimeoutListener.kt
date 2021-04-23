package com.hayate.common.manager.intf

import com.hayate.common.model.DelayMessage

/**
 * Created by Flame on 2020/06/11.
 */

interface IDelayTaskTimeoutListener {
    fun complete(delayMessage: DelayMessage)
}