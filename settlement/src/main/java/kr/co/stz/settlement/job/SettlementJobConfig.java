/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.settlement.job
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
package kr.co.stz.settlement.job;
import jakarta.persistence.EntityManagerFactory;
import kr.co.stz.settlement.Listener.JobLoggerListener;
import kr.co.stz.settlement.Listener.SettlementSkipListener;
import kr.co.stz.settlement.domain.Orders;
import kr.co.stz.settlement.domain.Settlement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SettlementJobConfig {
    
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    
    private final JobLoggerListener jobLoggerListener;
    private final SettlementSkipListener settlementSkipListener;
    
    @Bean
    public Job settlementJob(Step settlementStep) {
        return new JobBuilder("settlementJob", jobRepository)
                .listener(jobLoggerListener)
                .start(settlementStep)
                .build();
    }
    
    
    @Bean
    public Step settlementStep() {
        return new StepBuilder("settlementStep", jobRepository)
                .<Orders, Settlement>chunk(1000)
                .transactionManager(transactionManager)
                .reader(ordersReader(null))
                .processor(settlementItemProcessor())
                .writer(settlementJpaItemWriter())
                .faultTolerant()
                .retry(TransientDataAccessException.class)
                .retryLimit(3)
                .skip(IllegalArgumentException.class)
                .skipLimit(100)
                .listener(settlementSkipListener)
                .build();
    } 
    
    
    // itemReader
    
    @Bean
    @StepScope
    public JpaPagingItemReader<Orders> ordersReader(@Value("#{jobParameters.targetDate}") String targetDate) {
        log.info("[targetDate] 정산 집계 대상 날짜 : {}", targetDate);
        
        
        return new JpaPagingItemReaderBuilder<Orders>()
                .name("orderReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1000)
                .queryString("SELECT o FROM Orders o WHERE o.orderDate = :targetDate ORDER BY o.id")
                .parameterValues(Collections.singletonMap("targetDate", LocalDate.parse(targetDate)))
                .build();
    }
    
    // itemProcessor
    
    @Bean
    public ItemProcessor<Orders, Settlement> settlementItemProcessor() {
        return item -> {
            int fee = (int) (item.getAmount() * 0.03);
            int settlementAmount = item.getAmount() - fee;
            
            return new Settlement(item.getId(), item.getStoreName(), settlementAmount, LocalDate.now());
        };
    }
    
    // itemWriter
    /* OUTPUT */
    @Bean
    public JpaItemWriter<Settlement> settlementJpaItemWriter() {
        return new JpaItemWriterBuilder<Settlement>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
