package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item createItem(Item item, User user);

    Optional<Item> editItem(Long itemId , Item updateItem, User editUser);
    Optional<Item> findItemById(Long itemId);

    Optional<Item> findItemByName(String itemName);

    List<Item> findItemByCreator(String creatorName);

    List<Item> findNonApprovedItem();

    List<Item> findApprovedItem();

    List<Item> findAllItem();

    Item purchaseLog(Item item, User user, Long quantity);


}
