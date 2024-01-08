package com.bidpages.service;

import com.bidpages.mdm.vo.SysUserVO;
import com.bidpages.utils.ResponseResult;

public interface LoginService {
    ResponseResult login(SysUserVO user);
}
