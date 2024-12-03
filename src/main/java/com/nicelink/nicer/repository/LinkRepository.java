package com.nicelink.nicer.repository;

import com.nicelink.nicer.exeptions.link.LinkAlreadyExistsException;
import com.nicelink.nicer.exeptions.link.LinkNotFoundException;
import com.nicelink.nicer.exeptions.user.UserNotFoundException;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.dto.UpdateLinkDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    public LinkRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

//    SELECT
//::::::::::::::::::::::::::::::::::::::NEEEEEDDDD TO TEST!!!!!!!!!!!!!!!!!!:::::::::::::::::::::::::::::::::::
public String getOrigLinkByNiceLinkFAST(String niceLink) throws LinkNotFoundException {
    String sql = "SELECT orig_link FROM my_link WHERE nice_link = ?";

    try {
        // Using parameterized query to avoid SQL injection
        String origLink = jdbcTemplate.queryForObject(sql, String.class, niceLink);

        // Check if result is null (in case no such link exists)
        if (origLink == null) {
            throw new LinkNotFoundException("No link found for nice link: " + niceLink);
        }
        return origLink;
    } catch (EmptyResultDataAccessException e) {
        // Handle case where no result is found
        throw new LinkNotFoundException("No link found for nice link: " + niceLink);
    }
}

//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public List<Link> getLinksByParams(Link link){
        String sql = sqlSelectBuilder(link);

        return jdbcTemplate.query(sql, link.getParams(),Link::mapRow);

    }

    public List<ActionOnLinkOnUser> getLinksWithOwnerNameByNiceLink(String niceLink){
        String sql = "SELECT l.orig_link, l.nice_link, u.username FROM my_link l JOIN my_user u ON l.owner_id = u.id WHERE l.nice_link = ?";

        return jdbcTemplate.query(sql, new Object[]{niceLink}, ActionOnLinkOnUser::mapRow);
    }

    public Integer getLinkIdByNiceLink(String niceLink){
        String sql = "SELECT id FROM my_link WHERE nice_link = ?";

        Integer linkId = jdbcTemplate.queryForObject(sql, Integer.class, niceLink);

        if (linkId == null) {
            throw new LinkNotFoundException("No link found for nice link: " + niceLink);
        }

        return linkId;
    }

    private String sqlSelectBuilder(Link link){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT * FROM my_link WHERE");

        if(link.getOrig_link()!=null)sqlBuilder.append(" orig_link = ? AND");
        if(link.getNice_link()!=null)sqlBuilder.append(" nice_link = ? AND");
        if(link.getOwner_id()!=null)sqlBuilder.append(" owner_id = ? AND");
        if(link.getId()!=null)sqlBuilder.append(" id = ? AND");

        sqlBuilder.delete(sqlBuilder.length()-3, sqlBuilder.length());

        log.info("select sql builder:"+sqlBuilder);

        return sqlBuilder.toString();
    }

//    UPDATE

    public boolean updateNiceLinkByNiceLink(UpdateLinkDTO updateLinkDTO){

        log.info("link repository update link opened");

        String sql = "UPDATE my_link SET nice_link = ? WHERE nice_link = ?";

        if(jdbcTemplate.update(sql, updateLinkDTO.getParams()) > 0){
            return true;
        }else {
            throw new LinkNotFoundException("No link found for nice link: " + updateLinkDTO.getParams());
        }

    }

    //    INSERT
    public boolean createLink(Link link, String username) throws LinkAlreadyExistsException, UserNotFoundException {

        log.info("user repository createLink method:");

        UserRepository userRepository = new UserRepository(jdbcTemplate);
        Integer user_id = userRepository.getUserByUsername(username).getId();

        log.info("user id is:" + user_id);

        try {
            getOrigLinkByNiceLinkFAST(link.getNice_link());

            log.info("link already exists");
            throw new LinkAlreadyExistsException("This nice link already exists.");
        } catch (LinkNotFoundException e) {
            log.info("good news! link not found)");
//            every thing is fine!
        }

        link.setOwner_id(user_id);

        String sql = "INSERT INTO my_link (nice_link, orig_link, owner_id) VALUES (?,?,?)";

        return jdbcTemplate.update(sql,link.getParamsNoId()) > 0;
    }
}
