package com.game.xiaoyan.system.service.impl;

import com.game.xiaoyan.system.repository.AuthoritiesRepository;
import com.game.xiaoyan.system.repository.RoleAuthoritiesRepository;
import com.game.xiaoyan.common.exception.CustormException;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.system.entity.Authorities;
import com.game.xiaoyan.system.entity.RoleAuthorities;
import com.game.xiaoyan.system.service.AuthoritiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {
//    @Autowired
//    private AuthoritiesMapper authoritiesMapper;
//    @Autowired
//    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    @Resource
    private AuthoritiesRepository authoritiesRepository;
    @Resource
    private RoleAuthoritiesRepository roleAuthoritiesRepository;

    @Override
    public List<Authorities> listByUserId(Integer userId) {
        return authoritiesRepository.listByUserId(userId);
    }

    @Override
    public List<Authorities> list() {
        return authoritiesRepository.selectAllOrderByOrderNumber();
    }

    @Override
    public List<Authorities> listMenu() {
        return authoritiesRepository.selectAllByIsMenuOrderByOrderNumber(0);
    }

    @Override
    public List<Authorities> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return authoritiesRepository.listByRoleIds(roleIds);
    }

    @Override
    public List<Authorities> listByRoleId(Integer roleId) {
        return authoritiesRepository.listByRoleId(roleId);
    }

    @Override
    @Transactional
    public boolean add(Authorities authorities) {
        authorities.setCreateTime(new Date());
        authoritiesRepository.save(authorities);
        return  true;
    }

    @Override
    @Transactional
    public boolean update(Authorities authorities) {
        Authorities old = authoritiesRepository.findByAuthorityId(authorities.getAuthorityId());
        BeanUtils.copyNotNullProperties(authorities,old);
        authoritiesRepository.save(old);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Integer authorityId) {

        List<Authorities> childs = authoritiesRepository.selectAllByParentIdOrderByOrderNumber( authorityId);
        if (childs != null && childs.size() > 0) {
            childs.forEach(v->{
                delete(v.getAuthorityId());
            });
//            throw new CustormException("请先删除子节点",1);
        }
        roleAuthoritiesRepository.deleteByAuthorityId(authorityId);
        authoritiesRepository.deleteById(authorityId);
        return true;
    }

    @Override
    @Transactional
    public boolean addRoleAuth(Integer roleId, Integer authId) {
        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.setRoleId(roleId);
        roleAuthorities.setAuthorityId(authId);
        roleAuthorities.setCreateTime(new Date());
        roleAuthoritiesRepository.save(roleAuthorities);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteRoleAuth(Integer roleId, Integer authId) {
        roleAuthoritiesRepository.deleteByAuthorityIdAndRoleId(authId,roleId);
        return  true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleAuth(Integer roleId, List<Integer> authIds) {
        roleAuthoritiesRepository.deleteAllByRoleId(roleId);
        if (authIds != null && authIds.size() > 0) {
            authIds.forEach(v-> roleAuthoritiesRepository.save(new RoleAuthorities(roleId, v)));
        }
        return true;
    }

}
