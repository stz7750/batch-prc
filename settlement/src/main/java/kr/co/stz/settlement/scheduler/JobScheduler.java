/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.settlement.scheduler
 * 👤 AUTHOR   : stz
 * 🕒 CREATED  : 26. 8. 21.
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 📝 DESCRIPTION
 * -
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 🔄 CHANGE LOG
 * - DATE : 2026/08/21 | Author : stz | 최초 생성
 * ═════════════════════════════════════════════════════════════
 */
package kr.co.stz.settlement.scheduler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {
    
    private final JobOperator jobOperator;
    
    private final Job settlementJob;
    
    
    @Scheduled(cron = "0 42 11 * * *")
    public void runJob() throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("targetDate", LocalDate.now().minusDays(7).toString())
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        log.info("스케줄러 작동");
        
        jobOperator.start(settlementJob, jobParameters);
    }
}
