package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.ItemService;
import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String updateItem(ItemForm itemForm, UserForm userForm) {



        Item upItem = new Item();
        User userEdit = new User();
        userEdit.setUserId(userForm.getId());
        userEdit.setUserName(userForm.getName());
        userEdit.setUserType(userForm.getType());

        upItem.setItemEditor(userForm.getName());

        upItem.setItemNameEng(itemForm.getItemNameEng());
        upItem.setItemNameChn(itemForm.getItemNameChn());

        upItem.setItemTextEng(itemForm.getItemTextEng());
        upItem.setItemTextChn(itemForm.getItemTextChn());

        upItem.setItemCommissonPct(itemForm.getItemCommissonPct());

        upItem.setItemApproved(true);

        itemService.updateItem(itemForm.getItemId(), upItem, userEdit);
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
