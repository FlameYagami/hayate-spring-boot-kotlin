package com.hayate.auth.interceptor

import com.hayate.auth.annotation.AuthToken
import com.hayate.auth.constant.AuthConstant
import com.hayate.auth.manager.intf.AuthTokenManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Flame
 * @date 2020-03-16 15:17
 */

@Component
class AuthTokenInterceptor(@Autowired private val tokenManager: AuthTokenManager) : HandlerInterceptor {

    companion object{
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            log.debug("Handler not instance of Handler method")
            return true
        }
        val method = handler.method
        if (method.getAnnotation(AuthToken::class.java) != null
                || handler.beanType.getAnnotation(AuthToken::class.java) != null) {
            val token = request.getHeader(AuthConstant.AUTHORIZATION)
            log.debug("Request Token: $token")
            return if (token.isNotEmpty() && tokenManager.checkToken(token)) {
                true
            } else {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                false
            }
        }
        return true
    }
}