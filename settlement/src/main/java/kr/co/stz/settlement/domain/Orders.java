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
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Orders {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String customerName;
    
    private String storeName;
    
    private Integer amount;
    
    private LocalDate orderDate;

    public Orders(Long id, String customerName, String storeName, Integer amount, LocalDate orderDate) {
        this.id = id;
        this.customerName = customerName;
        this.storeName = storeName;
        this.amount = amount;
        this.orderDate = orderDate;
    }
}
