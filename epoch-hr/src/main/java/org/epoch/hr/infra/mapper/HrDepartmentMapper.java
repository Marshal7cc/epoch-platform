package org.epoch.hr.infra.mapper;


import org.epoch.core.algorithm.tree.TreeNode;
import tk.mybatis.mapper.common.Mapper;

import org.epoch.hr.domain.entity.HrDepartment;

import java.util.List;

/**
 * @author Marshal
 */
public interface HrDepartmentMapper extends Mapper<HrDepartment> {

    /**
     * 获取部门树状结构
     *
     * @param dto
     * @return
     */
    List<TreeNode> selectForTree(HrDepartment dto);

}
