package com.hayate.common.manager

import com.hayate.common.manager.intf.IDelayTaskTimeoutListener
import com.hayate.common.model.DelayMessage
import com.hayate.common.utils.TaskManager
import org.slf4j.LoggerFactory
import java.util.concurrent.DelayQueue
import java.util.stream.Collectors

/**
 * Created by Flame on 2020/06/09.
 */

object DelayTaskManager {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val delayMessages = DelayQueue<DelayMessage>()
    private val keys = arrayListOf<String>()
    private var iDelayTaskTimeoutListener: IDelayTaskTimeoutListener? = null

    init {
        val thread = Thread { start() }
        TaskManager.submit(thread)
    }

    private fun start() {
        while(true) {
            try {
                val delayMessage = delayMessages.take()
                if(null == iDelayTaskTimeoutListener) {
                    continue
                }
                iDelayTaskTimeoutListener?.complete(delayMessage)
                keys.remove(delayMessage.key)
            } catch(e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun setTimeoutListener(iDelayTaskTimeoutListener: IDelayTaskTimeoutListener) {
        this.iDelayTaskTimeoutListener = iDelayTaskTimeoutListener
    }

    fun put(delayMessage: DelayMessage, ignoreCheckLog: Boolean = false) {
        if(checkKey(delayMessage.key, ignoreCheckLog)) {
            delayMessages.put(delayMessage)
        }
    }

    operator fun get(key: String): DelayMessage? {
        val list = delayMessages.stream().filter { delayMessage: DelayMessage -> delayMessage.key == key }.collect(Collectors.toList())
        if(list.isEmpty()) {
            log.error("DelayMessage error: key({}) is not found", key)
            return null
        }
        return list[0]
    }

    fun remove(key: String) {
        delayMessages.removeIf { message: DelayMessage -> message.key == key }
        keys.remove(key)
    }

    operator fun contains(key: String): Boolean {
        return delayMessages.stream().anyMatch { delayMessage: DelayMessage -> delayMessage.key == key }
    }

    private fun checkKey(key: String, ignoreCheckLog: Boolean): Boolean {
        if(!keys.contains(key)) {
            return true
        }
        if(!ignoreCheckLog) {
            log.error("Check key error: {} has save", key)
        }
        return false
    }
}