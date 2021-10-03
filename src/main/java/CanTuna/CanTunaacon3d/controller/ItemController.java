package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.ItemService;
import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {


    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @ResponseBody
    @GetMapping("/items/all")
    public List<Item> list(Model model) {

        List<Item> items = itemService.viewItem();

        return items;
    }

    @GetMapping("/items/new")
    public String createForm() {
        return "/createItemForm";
    }

    @PostMapping("/items/new")
    public String createItem(ItemForm form) {
        User tempUser = new User();
        tempUser.setUserType(0L);
        tempUser.setUserName("tempCreator");

        Item item = new Item();
        item.setItemNameKor(form.getItemNameKor());
        item.setItemCreator(form.getItemCreator());
        item.setItemTextKor(form.getItemTextKor());
        item.setItemPrice(form.getItemPrice());

        itemService.createItem(item, tempUser);

        return "redirect:/items/all";
    }

    @GetMapping("/items/update")
    public String updateForm() {
        return "/updateItemForm";
    }

    @PostMapping("/items/update")
    public String updateItem(ItemForm form, User user) {

        Item updateItem = new Item();

        updateItem.setItemEditor(user.getUserName());

        updateItem.setItemNameEng(form.getItemNameEng());
        updateItem.setItemNameChn(form.getItemNameChn());

        updateItem.setItemTextEng(form.getItemTextEng());
        updateItem.setItemTextChn(form.getItemTextChn());

        updateItem.setItemCommissonPct(form.getItemCommissonPct());

        updateItem.setItemApproved(true);

        itemService.updateItem(form.getItemId(), updateItem, user);

        return "redirect:/items/all";
    }

    @ResponseBody
    @GetMapping("/items/approved")
    public List<Item> approvedItem() {

        List<Item> items = itemService.viewApprovedItem();

        return items;
    }

    @ResponseBody
    @GetMapping("/items/unapproved")
    public List<Item> unapprovedItem() {

        List<Item> items = itemService.nonApprovedItem();

        return items;
    }

}
