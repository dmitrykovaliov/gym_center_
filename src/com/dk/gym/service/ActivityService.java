package com.dk.gym.service;

import com.dk.gym.entity.Activity;
import com.dk.gym.exception.ServiceException;
import java.util.List;

public interface ActivityService {

    List<Activity> revertItems() throws ServiceException;
    String createItem(boolean updated, Activity entity) throws ServiceException;
    String updateItem(boolean updated, Activity entity) throws ServiceException;
    String deleteItem(String id) throws ServiceException;
    Activity findItemById(String id) throws ServiceException;
    boolean initItem(String name, String price, String note, Activity updateEntity);
}
