package com.research.demo.repository;

import com.research.demo.domain.ReportData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReportData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportDataRepository extends JpaRepository<ReportData, Long>, JpaSpecificationExecutor<ReportData> {

}
