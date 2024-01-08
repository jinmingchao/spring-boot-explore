package com.bidpages.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bidpages.mdm.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectDocumentationByPid(String UserName);
}
