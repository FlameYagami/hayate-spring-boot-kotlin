package com.hayate.common.utils


import org.slf4j.LoggerFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Flame on 2020/09/17.
 */

object TaskManager {

    private val log = LoggerFactory.getLogger(this::class.java)

    private val executorService: ExecutorService = Executors.newFixedThreadPool(8)

    fun submit(task: Runnable) {
        executorService.submit(task)
    }

    fun shutdown() {
        log.info("TaskManager shutdown start")
        executorService.shutdown()
        try {
            if(!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                log.error("TaskManager executor did not terminate in the 1000ms time")
                val droppedTasks = executorService.shutdownNow()
                log.error("TaskManager executor was abruptly shutdown, ${droppedTasks.size} tasks will not be executed", )
            }
        } catch(e: Exception) {
            log.error("TaskManager shutdown awaitTermination failed: ${e.message}")
            executorService.shutdownNow()
        }
        log.info("TaskManager shutdown finish")
    }
}