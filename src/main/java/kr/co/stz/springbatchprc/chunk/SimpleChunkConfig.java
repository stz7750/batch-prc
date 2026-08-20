/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.springbatchprc.chunk
 * 👤 AUTHOR   : stz
 * 🕒 CREATED  : 26. 8. 19.
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 📝 DESCRIPTION
 * -
 * ═════════════════════════════════════════════════════════════
 * ═════════════════════════════════════════════════════════════
 * 🔄 CHANGE LOG
 * - DATE : 2026/08/19 | Author : stz | 최초 생성
 * ═════════════════════════════════════════════════════════════
 */
package kr.co.stz.springbatchprc.chunk;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleChunkConfig {
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    
    
    @Bean
    public Job SimpleJob() {
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep())
                .build();
    }
    
    @Bean
    public Step simpleStep() {
        return new StepBuilder("simpleStep", jobRepository)
                .<String, String>chunk(2)
                .reader(menuReader())
                .processor(menuProcessor())
                .writer(menuWriter())
                .build();
    }
    
    // itemReader
    
    @Bean
    public ItemReader<String> menuReader() {
        return new ListItemReader<>(Arrays.asList(
                "ice americano", "latte", "mocha", "cappuccino", "espresso"
        ));
    }
    
    
    // itemProcessor
    
    @Bean
    public ItemProcessor<String, String> menuProcessor() {
        return String::toUpperCase;
    }
    
    
    // itemWriter
    
    @Bean
    public ItemWriter<String> menuWriter() {
        return items -> {
            log.info("--- 청크 쓰기 시작 ---");
            for (String item : items) {
                log.info("결과 : {}" , item);
            }
            log.info("--- 청크 쓰기 완료 ---");
        };
    }
    
    
    
}
