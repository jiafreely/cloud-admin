package com.cloud.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service.entity.UcenterMember;
import com.cloud.service.feign.ProduceService;
import com.cloud.service.mapper.UcenterMemberMapper;
import com.cloud.service.result.R;
import com.cloud.service.service.UcenterMemberService;
import com.cloud.service.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xjh
 * @since 2021-08-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private ProduceService produceService;

    @Override
    public Boolean UcenterAdd() {
        UcenterMember ucenterMember = new UcenterMember().setOpenid(RandomUtils.getFourBitRandom()).setMobile(RandomUtils.getFourBitRandom()).setNickname(RandomUtils.getFourBitRandom()).setSex(1);
        boolean save = this.save(ucenterMember);
        R add = produceService.add();
        return save;
    }
}
