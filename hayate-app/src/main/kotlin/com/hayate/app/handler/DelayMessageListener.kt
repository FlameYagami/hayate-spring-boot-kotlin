package com.hayate.app.handler

import com.hayate.common.manager.DelayTaskManager
import com.hayate.common.manager.intf.IDelayTaskTimeoutListener
import com.hayate.common.model.DelayMessage
import com.hayate.common.utils.GsonUtils.serializer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * Created by Flame on 2020/07/01.
 */

@Service
class DelayMessageListener : IDelayTaskTimeoutListener {

    companion object{
        val log = LoggerFactory.getLogger(this::class.java)
    }

    @PostConstruct
    fun lateInit() {
        DelayTaskManager.setTimeoutListener(this)
    }

    override fun complete(delayMessage: DelayMessage) {
        delayMessage.value?.apply {
            log.error("DelayMessage timeout => {}", serializer(delayMessage))
        }
    }
}