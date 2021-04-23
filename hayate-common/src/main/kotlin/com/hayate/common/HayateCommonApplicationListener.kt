package com.hayate.common

import com.hayate.common.utils.TaskManager
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

/**
 * Created by Flame on 2021/01/25.
 */

@Component
class HayateCommonApplicationListener {

    @PreDestroy
    fun onPreDestroy() {
        TaskManager.shutdown()
    }
}