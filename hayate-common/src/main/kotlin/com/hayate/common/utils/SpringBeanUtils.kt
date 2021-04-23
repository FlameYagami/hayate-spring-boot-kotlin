package com.hayate.common.utils

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

/**
 * @author Flame
 * @date 2020-12-30 10:08
 */

class SpringBeanUtils : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }

    companion object {

        lateinit var applicationContext: ApplicationContext

        /**
         * 通过Bean名字获取Bean
         */
        fun getBean(beanName: String): Any {
            return applicationContext.getBean(beanName)
        }

        /**
         * 通过Bean类型获取Bean
         */
        fun <T> getBean(beanClass: Class<T>): T {
            return applicationContext.getBean(beanClass)
        }

        /**
         * 通过Bean名字和Bean类型获取Bean
         */
        fun <T> getBean(beanName: String, beanClass: Class<T>): T {
            return applicationContext.getBean(beanName, beanClass)
        }
    }
}