package com.matrix.proxy.db.repository;

import com.matrix.proxy.db.entity.BasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @ClassName BasicInformationRepository
 * @Author QIANGLU
 * @Date 2020/6/10 11:22 上午
 * @Version 1.0
 */
public interface BasicInformationRepository extends JpaRepository<BasicInformation,Integer> {

    @Modifying
    @Query(value = "update BasicInformation set lastHeartbeat = ?1 where instanceId = ?2")
    public void updateByInstanceId(@Param(value = "lastHeartbeat") Date lastHeartbeat, @Param(value = "instanceId") String instanceId);

    /**
     * 根据lastHeartbeat查询应用实例列表信息
     * @param lastHeartbeat
     * @return
     */
    @Query("SELECT b FROM BasicInformation b where b.lastHeartbeat > ?1 order by b.instanceName ")
    List<BasicInformation> selectInstanceByLastHeartbeat( Date lastHeartbeat);
}
