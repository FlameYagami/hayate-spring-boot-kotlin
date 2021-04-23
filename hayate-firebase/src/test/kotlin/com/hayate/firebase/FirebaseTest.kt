package com.hayate.firebase

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.reflect.KParameter

/**
 * Created by Flame on 2020/07/08.
 */
class FirebaseTest {

    @Test
    fun test() {
        System.setProperty("proxyPort", "51837")
        System.setProperty("proxyHost", "127.0.0.1")
        val list: MutableList<String> = ArrayList()
        // token令牌
        list.add("ddnP_cgRS3iIz3N1hleC75:APA91bE6FdVH53PKWm-XNdc6sDHQ8ftkRuA7hbOFXH7xkTRwa0ArZeCdstqHnecEVWIlZwE-qOCPmbyG9x5Lx8hgFvBwABuRVVINDseIKDC5avH1IaHXZ9h7Xj8z9hMAiYHltCvwVtdY")
        val title = "Test Title"
        val message = "Test Message"
        KParameter.Kind.INSTANCE.push(list, title, message)
    }
}