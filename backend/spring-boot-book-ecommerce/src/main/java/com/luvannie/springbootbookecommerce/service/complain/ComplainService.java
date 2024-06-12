package com.luvannie.springbootbookecommerce.service.complain;

import com.luvannie.springbootbookecommerce.dao.ComplainRepository;
import com.luvannie.springbootbookecommerce.dao.OrderRepository;
import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.dto.ComplainDTO;
import com.luvannie.springbootbookecommerce.entity.Complain;
import com.luvannie.springbootbookecommerce.entity.Order;
import com.luvannie.springbootbookecommerce.entity.User;
import com.luvannie.springbootbookecommerce.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ComplainService implements IComplainService {
    private final ComplainRepository complainRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;




    @Override
    public Complain findComplainById(Long id) throws DataNotFoundException {
        return complainRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Complain not found"));
    }

    @Override
    @Transactional
    public Complain updateComplain(ComplainDTO complainDTO) throws Exception {
        Complain complain = complainRepository.findById(complainDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("Complain not found"));
        complain.setComplain(complainDTO.getComplain());
        complain.setFinish(complainDTO.isFinish());
        return complainRepository.save(complain);
    }

    @Override
    public void deleteComplain(long id) throws Exception{
        Complain complain = complainRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Complain not found"));
        complain.setFinish(true);
        complainRepository.save(complain);
    }

    @Override
    public List<Complain> findUnfinishedComplains() throws Exception {
        return complainRepository.findByIsFinishIsFalse();
    }



    @Override
    @Transactional
    public Complain createComplain(ComplainDTO complainDTO) throws Exception {
    User existingUser = userRepository
            .findById(complainDTO.getUserId())
            .orElseThrow(() ->
                    new DataNotFoundException(
                            "Cannot find user with id: "+complainDTO.getUserId()));

    Order existingOrder = orderRepository
            .findById(complainDTO.getOrderId())
            .orElseThrow(() ->
                    new DataNotFoundException(
                            "Cannot find order with id: "+complainDTO.getOrderId()));

    Complain newComplain = Complain.builder()
            .complain(complainDTO.getComplain())
            .userId(existingUser.getId())
            .orderId(existingOrder.getId())
            .isFinish(false)
            .build();
    return complainRepository.save(newComplain);
}
}
