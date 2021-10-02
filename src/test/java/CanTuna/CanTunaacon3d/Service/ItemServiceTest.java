package CanTuna.CanTunaacon3d.Service;

import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import CanTuna.CanTunaacon3d.repository.ExRateRepository;
import CanTuna.CanTunaacon3d.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired ExRateRepository exRateRepository;
    @Autowired UserService userService;

    @Test
    void createItem() {
        //given

        User user = new User();
        user.setUserName("user_item1");
        user.setUserType(0L);
        Long saveUser = userService.joinUser(user);

        Item item = new Item();
        item.setItemNameKor("item1");
        item.setItemPrice(10000D);
        item.setItemTextKor("item1의 설명입니다.");

        Long saveItem1 = itemService.createItem(item, user);
        System.out.println(saveItem1);
        System.out.println(item.getItemNameKor());
        System.out.println(item.getItemPrice());
        System.out.println(item.getItemTextKor());
        //when


        Item findItem = itemService.findItemByName(item.getItemNameKor()).get();
        System.out.println(findItem.getItemId());
        System.out.println(findItem.getItemCreator());
        System.out.println(findItem.getItemPrice());
        System.out.println(findItem.getItemTextKor());


        //then
        Assertions.assertThat(item.getItemNameKor()).isEqualTo(findItem.getItemNameKor());

    }


    @Test
    void updateItem() {
        //given

        //when

        //then
    }

    @Test
    void viewItem() {
        //given

        //when

        //then
    }

    @Test
    void buyItem() {
        //given

        //when

        //then
    }

    @Test
    void calcPrice() {
        //given

        //when

        //then
    }
}