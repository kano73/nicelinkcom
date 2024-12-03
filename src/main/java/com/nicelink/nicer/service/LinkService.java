package com.nicelink.nicer.service;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
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

    public boolean createLink(Link link, String username) {

      log.info("link service method create link opened");

      return linkRepository.createLink(link,username);
    }
}
