/**
 * ═════════════════════════════════════════════════════════════
 * 📄 FILE     : null.java
 * 📁 PACKAGE  : spring-batch-prc-kr.co.stz.settlement.domain
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
package kr.co.stz.settlement.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Settlement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long orderId;
    
    private String storeName;
    
    private Integer settlementAmount;
    
    private LocalDate settlementDate;

    public Settlement(Long orderId, String storeName, Integer settlementAmount, LocalDate settlementDate) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.settlementAmount = settlementAmount;
        this.settlementDate = settlementDate;
    }
}
