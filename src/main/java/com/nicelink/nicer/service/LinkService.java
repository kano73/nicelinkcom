package com.nicelink.nicer.service;
import com.nicelink.nicer.exeptions.link.InvalidLinkException;
import com.nicelink.nicer.exeptions.link.UserDoesNotOwnThisLinkException;
import com.nicelink.nicer.exeptions.user.ValidationException;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import com.nicelink.nicer.model.LinkResult;
import com.nicelink.nicer.model.dto.UpdateLinkDTO;
import com.nicelink.nicer.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LinkService {

  private LinkRepository linkRepository;

  public LinkService(LinkRepository linkRepository) {
      this.linkRepository = linkRepository;
  }

//  SELECT

    public String getOrigLinkByNiceLinkFAST(String niceLink) {
      return linkRepository.getOrigLinkByNiceLinkFAST(niceLink);
    }

    public List<LinkResult> getAllLinksForUserByUsername(String username) {

      if(username==null){
        throw new ValidationException("emm...","user validation failed");
      }

      return linkRepository.getAllLinksForUserByUsername(username);
    }

    public List<Link> getAllLinksByParams(Link link) {
      return linkRepository.getLinksByParams(link);
    }

    public List<ActionOnLinkOnUser> getLinksWithOwnerByNiceLink(String niceLink) {
      return linkRepository.getLinksWithOwnerNameByNiceLink(niceLink);
    }

    public Integer getLinkIdByNiceLink(String niceLink) {
      return linkRepository.getLinkIdByNiceLink(niceLink);
    }

//    UPDATE

    public boolean updateNiceLinkByNiceLink(UpdateLinkDTO updateLinkDTO) {
      log.info("link service update link opened");
      return linkRepository.updateNiceLinkByNiceLink(updateLinkDTO);
    }

//    INSERT

    public boolean createLink(Link link) {
      log.info("link service method create link opened");

      return linkRepository.createLink(link);
    }

//    DELETE
    public boolean deleteLinkByNiceLink(String niceLink) throws InvalidLinkException {
      log.info("link service method delete link opened");

      return linkRepository.deleteLinkByNiceLinkAndActionsOnIt(niceLink);
    }

  public void validateOwnerShip(Integer link_id , Integer userId) {
    log.info("link service method isThisNiceLinkBelongsToThisUserId opened");

    if(!linkRepository.isUserOwnThisNiceLinkByUserId(link_id ,userId)){
      throw new UserDoesNotOwnThisLinkException("user does not own this nice link");
    }
  }
}
