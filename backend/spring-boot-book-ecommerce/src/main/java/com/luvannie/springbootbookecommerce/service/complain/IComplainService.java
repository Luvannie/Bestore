package com.luvannie.springbootbookecommerce.service.complain;

import com.luvannie.springbootbookecommerce.dto.ComplainDTO;
import com.luvannie.springbootbookecommerce.entity.Complain;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import com.luvannie.springbootbookecommerce.responses.complain.ComplainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IComplainService {
    Complain findComplainById(Long id) throws DataNotFoundException;
    Complain updateComplain(ComplainDTO complain) throws Exception;


    void deleteComplain(long id) throws Exception;

    Page<ComplainResponse> findUnfinishedComplains(PageRequest pageRequest) throws Exception;
    Complain createComplain(ComplainDTO complain) throws Exception;
}
