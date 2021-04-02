package com.galaxy.portal.common.customize.aspect;

import com.galaxy.portal.common.constant.enums.ApiException;
import com.galaxy.portal.common.constant.enums.ApiResultEnum;

/**
 * 更新重试异常
 */
public class TryAgainException extends ApiException {

    public TryAgainException(ApiResultEnum apiResultEnum) {
        super(apiResultEnum);
    }

}
