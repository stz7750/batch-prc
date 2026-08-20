/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.springbatchprc
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
package kr.co.stz.springbatchprc.tasklet;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.batch.infrastructure.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CafeJobConfig {
    
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private int cnt = 0;
    private final int ORDER_TARGET = 5;
    // 1. Cafe 문을 열기 -> openCafeStep
    // 2. coffee 만들기 (5잔) -> makeCoffeeStep
    // 3. 마감청소 및 퇴근 -> closeCafeStep
    
    @Bean
    public Job cafeJob() {
        return new JobBuilder("cafeJob", jobRepository)
                .start(openCafeStep())
                .next(makeCoffeeStep())
                .next(closeCafeStep())
                .build();
    }
    
    @Bean
    public Step openCafeStep() {
        return new StepBuilder("openCafeStep",jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("[오픈] 카페 문을 열고 머신을 예열 합니다.");
                    return RepeatStatus.FINISHED;
                }, new ResourcelessTransactionManager())
                .build();
    }
    
    @Bean
    public Step makeCoffeeStep() {
        return new StepBuilder("makeCoffeeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    cnt++;
                    System.out.println("[제조] 아메리카노 " + cnt + "잔 째 완성!");
                    if(cnt < ORDER_TARGET) {
                        return RepeatStatus.CONTINUABLE;
                    }else{
                        System.out.println("[완료] 주문하신 커피" + cnt + "잔 나왔습니다.");
                        return RepeatStatus.FINISHED;
                    }
                    
                },platformTransactionManager)
                .build();
    }
    
    @Bean
    public Step closeCafeStep() {
        return new StepBuilder("closeCafeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("[클로즈] 카페의 문을 닫습니다.");
                    return RepeatStatus.FINISHED;
                },platformTransactionManager)
                .build();
    }
    
}
