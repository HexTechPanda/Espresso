package com.espresso.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Menu Category Entity
 *
 * @author Mingze Ma
 */
@Getter
@Setter
@Entity
@Table(name = "espresso_menu_category")
@EntityListeners(AuditingEntityListener.class)
public class MenuCategory {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid") //generate 32length UUID
    @Column(length = 40)
    private String id;

    private String categoryName;

    @Column(columnDefinition = "boolean default true")
    private boolean activeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_outline_id")
    private MenuOutline menuOutline;
}
