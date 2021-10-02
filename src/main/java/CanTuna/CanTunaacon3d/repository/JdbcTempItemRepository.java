package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

public class JdbcTempItemRepository implements ItemRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTempItemRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Item createItem(Item item) {

        /*jdbcTemplate.update("INSERT INTO ITEM (name_kor = ?, text_kor = ?,price = ?,reg_date = ?, updt_date = ?) ", itemRowMapper(),
                                    item.getItemNameKor(), item.getItemTextKor(),item.getItemPrice(), getDate(), getDate());
        List<Item> result = jdbcTemplate.query("select * from ITEM where name_kor = ?", itemRowMapper(), item.getItemNameKor());
        item.setItemId(result.stream().findAny().get().getItemId());
        return item;*/



        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ITEM").usingColumns("name_kor","text_kor","price", "approved", "reg_date", "updt_date" ).usingGeneratedKeyColumns("id");

        Map<String, Object> param = new HashMap<>();

        param.put("name", item.getItemNameKor());
        param.put("text_kor", item.getItemTextKor());
        param.put("price", item.getItemPrice());

        //상품의 approved 항목 default value
        param.put("approved", false);

        param.put("reg_date", getDate());
        param.put("updt_date", getDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));

        item.setItemId(key.longValue());

        return item;
    }
    @Override
    public Optional<Item> findItemById(String itemId) {
        List<Item> result = jdbcTemplate.query("select * from ITEM where id = ?", itemRowMapper(), itemId);
        return result.stream().findAny();
    }

    @Override
    public Optional<Item> editItem(Long itemId , Item updateItem, User editUser) {

        List<Item> result = jdbcTemplate
                .query("update ITEM " +
                        "set (name_kor = ?, name_eng = ?, name_chn = ?, " +
                                "text_kor = ?, text_eng = ?, text_chn = ?, " +
                                "editor = ?, commission = ?, approved = ?, " +
                                "updt_date = ?) " +
                                "where id = ?", itemRowMapper(),
                        updateItem.getItemNameKor(),  updateItem.getItemNameChn(),        updateItem.getItemNameEng(),
                        updateItem.getItemTextKor(),        updateItem.getItemTextEng(),        updateItem.getItemTextCHN(),
                        editUser.getUserId(),               updateItem.getItemCommissonPct(),   updateItem.getItemApproved(),
                        getDate(),
                        itemId);
        List<Item> targetResult = jdbcTemplate.query("select * from ITEM where id = ?", itemRowMapper(), itemId);
        return targetResult.stream().findAny();
    }



    @Override
    public Optional<Item> findItemByName(String itemName) {
        List<Item> result = jdbcTemplate.query("select * from ITEM where name_kor = ?", itemRowMapper(), itemName);

        return result.stream().findAny();
    }

    @Override
    public List<Item> findItemByCreator(String creatorName) {

        List<Item> result = jdbcTemplate.query("select * from ITEM where creator = ?", itemRowMapper(), creatorName);
        return result;
    }

    @Override
    public List<Item> findNonApprovedItem() {
        List<Item> result = jdbcTemplate.query("select * from ITEM where approved = ?", itemRowMapper(), false);
        return result;
    }

    @Override
    public List<Item> findAllItem() {
        return jdbcTemplate.query("select * from ITEM", itemRowMapper());
    }

    @Override
    public Item purchaseLog(Item item, User user, Long quantity) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("PURCHASED_LIST").usingGeneratedKeyColumns("id");

        Map<String, Object> param = new HashMap<>();

        param.put("item_id", item.getItemId());
        param.put("creator", item.getItemCreator());
        param.put("quantity", quantity);
        param.put("price", item.getItemPrice());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Calendar calender = Calendar.getInstance();
        String strDate = sdfDate.format(calender.getTime());

        param.put("reg_date", getDate());
        param.put("updt_date", getDate());

        return item;
    }


    private RowMapper<Item> itemRowMapper() {
        return(rs, row) ->{
            Item item = new Item();
            item.setItemId(rs.getLong("id"));
            item.setItemNameKor(rs.getString("name_kor"));
            item.setItemCreator(rs.getString("creator"));
            item.setItemApproved(rs.getBoolean("approved"));
            System.out.println("RowMapper :::" +  item.getItemId() + item.getItemNameKor() +item.getItemCreator() );
            return item;

        };
    }

    //DB 상에 등록일, 수정일을 남기기 위한 getDate함수
    private Long getDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Calendar calender = Calendar.getInstance();
        String strDate = sdfDate.format(calender.getTime());

        return Long.parseLong(strDate);
    }


}
