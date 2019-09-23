package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.YzjSysAuthorityMapper;
import com.jc.model.YzjSysAuthority;
import com.jc.service.YzjSysAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class YzjSysAuthorityServiceImpl implements YzjSysAuthorityService {
    @Autowired
    private YzjSysAuthorityMapper yzjSysAuthorityMapper;
    @Override
    public List<YzjSysAuthority> selectByPrimary(String page, String limit) {
        PageRange pageRange = new PageRange(page,limit);
        List<YzjSysAuthority> resulset = yzjSysAuthorityMapper.selectByPrimary(pageRange.getStart(),pageRange.getEnd());
        return resulset;
    }
}
