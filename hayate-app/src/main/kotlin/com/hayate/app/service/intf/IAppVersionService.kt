package com.hayate.app.service.intf

import com.baomidou.mybatisplus.extension.service.IService
import com.hayate.app.model.entity.AppVersion

/**
 * @author Flame
 * @date 2020-05-18 16:45
 */

interface IAppVersionService : IService<AppVersion> {
    fun findVersionByPlatform(platform: String): AppVersion?
}