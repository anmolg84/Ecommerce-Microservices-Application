package com.anmol.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity // Declaring Order Model As JPA-Entity
@Table(name = "t_orders") // Storing Orders in t_orders table in Mysql
@Data @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    // RelationShip Between Order And OrderLineItems
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;
}
