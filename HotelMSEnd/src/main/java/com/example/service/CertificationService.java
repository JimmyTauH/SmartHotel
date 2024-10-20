package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Certification;
import com.example.exception.CustomException;
import com.example.mapper.CertificationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2023/11/30 21:37
 */
@Service
public class CertificationService {

    @Resource
    private CertificationMapper certificationMapper;


    public void add(Certification certification) {
        if (certification.getStatus()==null){
            certification.setStatus("待审核");
        }
        if (certification.getStatus()!=null){
            String status = certification.getStatus();
            if (status.equals("待审核")||status.equals("审核通过")||status.equals("审核不通过")){
                certification.setStatus("待审核");
            }
        }
        // 判断是否已提交认证信息
        Certification selectedByUserId = certificationMapper.selectByUserId(certification.getUserId());
        if (selectedByUserId != null){
            throw new CustomException(ResultCodeEnum.CERTIFICATION_EXIST_ERROR);
        }
        certificationMapper.add(certification);
    }


    public void update(Certification certification) {
        certificationMapper.update(certification);
    }

    public void deleteById(Integer id) {
        certificationMapper.deleteById(id);
    }

    public Certification selectById(Integer id) {
        return certificationMapper.selectById(id);
    }

    public List<Certification> selectAll(Certification certification){
        return certificationMapper.selectAll(certification);
    }


    public PageInfo<Certification> selectPage(Certification certification, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Certification> list = certificationMapper.selectAll(certification);
        return PageInfo.of(list);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            certificationMapper.deleteById(id);
        }
    }

    /**
     * 查询当前用户的认证信息
     */
    public Certification selectUserCertification() {
        Account currentUser = TokenUtils.getCurrentUser();
        return certificationMapper.selectByUserId(currentUser.getId());
    }

    /**
     * 根据UserId查询当前用户的认证信息
     */
    public Certification selectByUserId(Integer userId) {
        return certificationMapper.selectByUserId(userId);
    }

    public void deleteByUserId(Integer userId) {
        certificationMapper.deleteByUserId(userId);
    }
}