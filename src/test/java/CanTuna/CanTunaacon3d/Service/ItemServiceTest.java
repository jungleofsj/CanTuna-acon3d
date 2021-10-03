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

import java.util.List;

@SpringBootTest

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
        item.setItemNameKor("item3");
        item.setItemCreator("user_item1");
        item.setItemPrice(10000D);
        item.setItemTextKor("item1의 설명입니다.");

        Item saveItem1 = itemService.createItem(item, user);

        System.out.println("저장된 SavedItem1");
        System.out.println(saveItem1.getItemId());
        System.out.println(saveItem1.getItemNameKor());
        System.out.println(saveItem1.getItemPrice());
        System.out.println(saveItem1.getItemTextKor());
        System.out.println("-----------------------");
        //when


        //Item findItem = itemService.findItemById(item.getItemNameKor()).get();
        Item findItembyName = itemService.findItemByName(saveItem1.getItemNameKor()).get();
        System.out.println("저장된 findItembyName");
        System.out.println(findItembyName.getItemId());
        System.out.println(findItembyName.getItemCreator());
        System.out.println(findItembyName.getItemPrice());
        System.out.println(findItembyName.getItemTextKor());
        System.out.println("-----------------------");

        Item findItembyId = itemService.findItemById(findItembyName.getItemId()).get();
        System.out.println("저장된 findItembyId");
        System.out.println(findItembyId.getItemId());
        System.out.println(findItembyId.getItemCreator());
        System.out.println(findItembyId.getItemPrice());
        System.out.println(findItembyId.getItemTextKor());


        //then
        Assertions.assertThat(item.getItemNameKor()).isEqualTo(findItembyId.getItemNameKor());

    }


    @Test
    void updateItem() {
        //given
        /*Creator*/
        User userCreat = new User();
        userCreat.setUserName("user_creat1");
        userCreat.setUserType(0L);
        Long saveUser = userService.joinUser(userCreat);

        Item item = new Item();
        item.setItemNameKor("item3");
        //item.setItemCreator("user_item1");
        item.setItemPrice(10000D);
        item.setItemTextKor("item1의 설명입니다.");
        Item saveItem1 = itemService.createItem(item, userCreat);

        Item itemUP = new Item();
        itemUP.setItemNameKor("item3");
        //item.setItemCreator("user_item1");
        itemUP.setItemPrice(10000D);
        itemUP.setItemApproved(true);
        itemUP.setItemTextKor("item1의 한글설명입니다.");
        itemUP.setItemTextEng("item1의 영어설명입니다.");
        itemUP.setItemTextChn("item1의 중어설명입니다.");
        //ItemUP = itemService.createItem(itemUP, userCreat);

        User userEdit = new User();
        userEdit.setUserName("user_edit1");
        userEdit.setUserType(1L);
        itemService.updateItem(saveItem1.getItemId(), itemUP, userEdit);

        //when
        Item findItembyId = itemService.findItemById(saveItem1.getItemId()).get();
        //then
        System.out.println("edit된 findItembyId");
        System.out.println(findItembyId.getItemId());
        System.out.println(findItembyId.getItemCreator());
        System.out.println(findItembyId.getItemPrice());
        System.out.println(findItembyId.getItemTextKor());
        System.out.println(findItembyId.getItemTextEng());
        System.out.println(findItembyId.getItemTextChn());
    }

    @Test
    void viewItem() {
        //given

        /*Creator*/
        User userCreat = new User();
        userCreat.setUserName("user_creat1");
        userCreat.setUserType(0L);
        Long saveUser = userService.joinUser(userCreat);

        Item item1= new Item();
        item1.setItemNameKor("item3");
        //item.setItemCreator("user_item1");
        item1.setItemPrice(10000D);
        item1.setItemTextKor("item3의 설명입니다.");
        Item saveItem1 = itemService.createItem(item1, userCreat);

        Item item2 = new Item();
        item2.setItemNameKor("item4");
        //item.setItemCreator("user_item1");
        item2.setItemPrice(20000D);
        item2.setItemTextKor("item4의 설명입니다.");
        Item saveItem2 = itemService.createItem(item2, userCreat);
        //when
        List<Item> resultLIst = itemRepository.findAllItem();
        Item temp = resultLIst.get(0);
        //then
        System.out.println("resultLIst id ::: " +  temp.getItemId());
        System.out.println("resultLIst namekor ::: " +  temp.getItemNameKor());
        System.out.println("resultLIst nameeng ::: " +  temp.getItemNameEng());
        System.out.println("resultLIst namechn ::: " +  temp.getItemNameChn());
        System.out.println("resultLIst creator ::: " +  temp.getItemCreator() );
        System.out.println("resultLIst approved ::: " +  temp.getItemApproved());
        System.out.println("resultLIst textkor ::: " +  temp.getItemTextKor());
        System.out.println("resultLIst texteng ::: " +  temp.getItemTextEng());
        System.out.println("resultLIst textchn ::: " +  temp.getItemTextChn());

    }

    @Test
    void buyItem() {
        //given

        //when
        List<Item> resultLIst = itemRepository.findApprovedItem();

        System.out.println(resultLIst.size());

    }

    @Test
    void calcPrice() {
        //given

        //when

        //then
    }
}