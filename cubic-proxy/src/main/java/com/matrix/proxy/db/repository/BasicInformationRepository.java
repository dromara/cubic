package com.matrix.proxy.db.repository;

import com.matrix.proxy.db.entity.BasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName BasicInformationRepository
 * @Author QIANGLU
 * @Date 2020/6/10 11:22 上午
 * @Version 1.0
 */
public interface BasicInformationRepository extends JpaRepository<BasicInformation,Integer> {
}
