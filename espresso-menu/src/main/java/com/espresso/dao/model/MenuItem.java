package com.espresso.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Menu Item Entity
 *
 * @author Mingze Ma
 */
@Getter
@Setter
@Entity
@Table(name = "espresso_menu_item")
@EntityListeners(AuditingEntityListener.class)
public class MenuItem {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid") //generate 32length UUID
    @Column(length = 40)
    private String id;

    private String name;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "boolean default true")
    private boolean available;

    @Column(columnDefinition = "int default 0")
    private Integer price;

    @Column(columnDefinition = "bigint default 0")
    private Long sales;

    private Integer discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_outline_id")
    private MenuOutline menuOutline;
}
