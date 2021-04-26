package com.hayate.common.utils


import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Flame on 2020/05/19.
 */

object FileUtils {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun writeFile(fileBytes: ByteArray, desPath: String): Boolean {
        return try {
            val file = File(desPath)
            if(!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            if(!file.exists()) {
                file.createNewFile()
            }
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(fileBytes)
            fileOutputStream.close()
            true
        } catch(e: Exception) {
            log.error("Write file error => ${e.message}")
            false
        }
    }

    fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        if(file.exists()) {
            return file.delete()
        }
        log.error("Delete file error => file path($filePath) is not exist")
        return false
    }
}