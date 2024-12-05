package com.nicelink.nicer.repository;

import com.nicelink.nicer.exeptions.link.InvalidLinkException;
import com.nicelink.nicer.exeptions.link.LinkAlreadyExistsException;
import com.nicelink.nicer.exeptions.link.LinkNotFoundException;
import com.nicelink.nicer.exeptions.user.UserNotFoundException;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.LinkResult;
import com.nicelink.nicer.model.dto.UpdateLinkDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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

        log.info("orig link:"+ origLink);

        // Check if result is null (in case no such link exists)
        if (origLink == null) {
            throw new LinkNotFoundException("No link found for nice link: " + niceLink+"orig link="+origLink);
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

    public List<LinkResult> getAllLinksForUserByUsername(String username){
        String sql = " SELECT l.id ,l.orig_link, l.nice_link, ( SELECT COUNT(a.id) FROM my_action a WHERE a.link_id = l.id ) as actions_num FROM my_link l JOIN my_user u ON u.id = l.owner_id WHERE u.username = ? ";

        return jdbcTemplate.query(sql,new Object[]{username},LinkResult::mapRow);
    }

    public boolean isUserOwnThisNiceLinkByUserId(Integer link_id ,Integer userId){
        log.info("link repo isUserOwnThisNiceLinkByUserId opened");
        String sql = "SELECT owner_id FROM my_link WHERE id = ?";

        return Objects.equals(userId, jdbcTemplate.queryForObject(sql, Integer.class, link_id));
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

        try{
            if(getOrigLinkByNiceLinkFAST(updateLinkDTO.getNice_link_new())!=null) {
                throw new LinkAlreadyExistsException("Link already exists");
            }
        }catch (LinkNotFoundException e){
            log.info("good: link does not exists");
        }

        String sql = "UPDATE my_link SET nice_link = ? WHERE nice_link = ?";

        if(jdbcTemplate.update(sql, updateLinkDTO.getParams()) > 0){
            return true;
        }else {
            throw new LinkNotFoundException("No link found for nice link: " + updateLinkDTO.getParams());
        }
    }

    //    INSERT
    public boolean createLink(Link link) throws LinkAlreadyExistsException, UserNotFoundException {

        log.info("user repository createLink method:");

        try {
            getOrigLinkByNiceLinkFAST(link.getNice_link());

            log.info("link already exists");
            throw new LinkAlreadyExistsException("This nice link already exists.");
        } catch (LinkNotFoundException e) {
            log.info("good news! link not found)");
//            every thing is fine!
        }

        String sql = "INSERT INTO my_link (nice_link, orig_link, owner_id) VALUES (?,?,?)";

        return jdbcTemplate.update(sql,link.getParamsNoId()) > 0;
    }

//    DELETE
    public boolean deleteLinkByNiceLinkAndActionsOnIt(String niceLink) throws InvalidLinkException {
        log.info("link repository delete link opened");

        if (niceLink==null){
            throw new InvalidLinkException("Link is null ");
        }

        String sql = "DELETE FROM my_link WHERE nice_link = ? ";
        log.info("trying to delete from db sql:"+sql);

        return jdbcTemplate.update(sql, niceLink) > 0;
    }
}
