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


import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

@Slf4j
public class CafeJobTasklet implements Tasklet {
    
    private int cakeCnt = 0;
    
    private final int ORDER_TARGET = 10;
    
    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        cakeCnt++;
        log.info("케이크를 만들고 있습니다.({}/{})",cakeCnt,ORDER_TARGET);
        
        if(cakeCnt >= ORDER_TARGET) {
            log.info("목표한 모든 케이크를 다 만들었습니다. 가게를 오픈하세요.");
            return RepeatStatus.FINISHED;
        } 
        return RepeatStatus.CONTINUABLE;
    }
    
    
    
}
