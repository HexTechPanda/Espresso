package com.espresso.dao.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Menu Outline Entity
 *
 * @author Mingze Ma
 */
@Getter
@Setter
@Entity
@Table(name = "espresso_menu_outline")
@EntityListeners(AuditingEntityListener.class)
public class MenuOutline implements Serializable {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid") //generate 32length UUID
    @Column(length = 40)
    private String id;

    @Column(columnDefinition = "boolean default false")
    private boolean activeStatus;

    private String menuName;

    @Column(columnDefinition = "boolean default false")
    private boolean defaultStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastUpdatedDate;

}
