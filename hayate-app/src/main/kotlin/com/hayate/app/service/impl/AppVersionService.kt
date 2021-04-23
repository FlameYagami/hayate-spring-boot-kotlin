package com.hayate.app.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hayate.app.constant.DbConstant
import com.hayate.app.mapper.AppVersionMapper
import com.hayate.app.model.entity.AppVersion
import com.hayate.app.service.intf.IAppVersionService
import org.springframework.stereotype.Service

/**
 * @author Flame
 * @date 2020-05-18 16:46
 */

@Service
class AppVersionService : ServiceImpl<AppVersionMapper, AppVersion>(), IAppVersionService {

    override fun findVersionByPlatform(platform: String): AppVersion? {
        val qwAppVersion = QueryWrapper<AppVersion>()
                .eq(DbConstant.PLATFORM, platform)
        return getOne(qwAppVersion)
    }
}