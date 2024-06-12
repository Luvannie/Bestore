package com.luvannie.springbootbookecommerce.service.complain;

import com.luvannie.springbootbookecommerce.dto.ComplainDTO;
import com.luvannie.springbootbookecommerce.entity.Complain;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;

import java.util.List;

public interface IComplainService {
    Complain findComplainById(Long id) throws DataNotFoundException;
    Complain updateComplain(ComplainDTO complain) throws Exception;


    void deleteComplain(long id) throws Exception;

    List<Complain> findUnfinishedComplains() throws Exception;
    Complain createComplain(ComplainDTO complain) throws Exception;
}
