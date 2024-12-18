package com.nicelink.nicer.service;

import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.ActionOnLinkOnUser;
import com.nicelink.nicer.repository.ActionRepository;
import com.nicelink.nicer.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

//    SELECT

    public List<Action> getAllActionsOnLinkById(Integer linkId) {
        return actionRepository.getAllActionsOnLinkById(linkId);
    }

//    INSERT

    public boolean addAction(Action action) {
        return actionRepository.addAction(action);
    }
}
