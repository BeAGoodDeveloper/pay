package com.pay.domain.money;

import com.pay.domain.room.Room;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spread_money", indexes = {
        @Index(name = "idx_spread_token", columnList = "token")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token"})
})
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"room", "receiveMonies"})
@NoArgsConstructor
public class SpreadMoney implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spread_money_idx", insertable = false, nullable = false)
    private Long idx;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_idx")
    private Room room;

    @Column(name = "token")
    private String token;

    @Column(name = "amount_money")
    private Long amountMoney;

    @Column(name = "user_count")
    private Long userCount;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "spread_user_id")
    private String spreadUserId;

    @OneToMany
    @JoinColumn(name = "spread_money_idx")
    private List<ReceiveMoney> receiveMonies;

    @Builder
    public SpreadMoney(Room room, String token, Long spreadUserCount, Long spreadAmountMoney, String spreadUserId, List<ReceiveMoney> receiveMonies) {
        this.room = room;
        this.token = token;
        this.spreadUserId = spreadUserId;
        this.amountMoney = spreadAmountMoney;
        this.userCount = spreadUserCount;
        this.startTime = LocalDateTime.now();
        this.receiveMonies = receiveMonies;
    }
}