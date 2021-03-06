package com.pay.application;

import com.pay.domain.HeaderRequestVO;
import com.pay.domain.money.MoneyService;
import com.pay.domain.money.SpreadMoney;
import com.pay.domain.money.vo.SpreadMoneyRequestVO;
import com.pay.domain.room.Room;
import com.pay.domain.room.RoomService;
import com.pay.domain.token.TokenService;
import com.pay.domain.user.User;
import com.pay.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class MoneyApplicationImplTest {

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Test
    @Rollback(false)
    void 뿌리기() {

        Long spreadAmountMoney = 50000L;
        Long spreadUserCount = 100L;
        String userId = "user_01";
        String roomId = "room_01";

        HeaderRequestVO headerRequestVO = HeaderRequestVO.builder()
                .userId(userId)
                .roomId(roomId)
                .build();

        SpreadMoneyRequestVO spreadMoneyRequestVO = SpreadMoneyRequestVO.builder()
                .spreadAmountMoney(spreadAmountMoney)
                .spreadUserCount(spreadUserCount)
                .build();

        User user = User.builder().userId(userId).build();
        User createUser = userService.createUser(user);
        Assertions.assertEquals(user.getUserId(), createUser.getUserId());

        Room room = Room.builder().roomId(roomId).user(user).build();
        Room createRoom = roomService.createRoom(room);
        Assertions.assertEquals(room.getRoomId(), createRoom.getRoomId());

        String token = tokenService.getToken(userId);
        moneyService.createSpreadMoney(headerRequestVO, spreadMoneyRequestVO, token);

        SpreadMoney spreadMoney = moneyService.findSpreadMoneyByToken(token);
        Assertions.assertNotNull(spreadMoney);

        Assertions.assertEquals(spreadMoney.getAmountMoney(), spreadAmountMoney);
        Assertions.assertEquals(spreadMoney.getSpreadUserId(), userId);
        Assertions.assertEquals(spreadMoney.getRoom().getRoomId(), roomId);
        Assertions.assertEquals(spreadMoney.getReceiveMonies().size(), spreadUserCount);
    }

    @Test
    void 조회() {

        String userId = "user_01";
        String roomId = "room_01";

        HeaderRequestVO headerRequestVO = HeaderRequestVO.builder()
                .userId(userId)
                .roomId(roomId)
                .build();

        String token = "716";
        Pageable pageable = PageRequest.of(0, 20);

        Page<SpreadMoney> spreadMonies = moneyService.spreadList(headerRequestVO, token, pageable);

        for (SpreadMoney spreadMoney : spreadMonies) {
            //7일 동안 조회 가능
            if (spreadMoney.getStartTime().isBefore(LocalDateTime.now().minusDays(7))) {
                Assertions.fail();
            }

            if (!spreadMoney.getSpreadUserId().equals(userId)) {
                Assertions.fail();
            }
        }
    }
}