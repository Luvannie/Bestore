package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.dto.ComplainDTO;
import com.luvannie.springbootbookecommerce.entity.Complain;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.complain.ComplainResponse;
import com.luvannie.springbootbookecommerce.service.complain.ComplainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/complains")
@RequiredArgsConstructor
public class ComplainController {
    private final ComplainService complainService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getComplainById(@PathVariable Long id) throws Exception {
        Complain complain = complainService.findComplainById(id);
        ComplainResponse complainResponse = ComplainResponse.fromComplain(complain);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get complain successfully")
                .status(HttpStatus.OK)
                .data(complainResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateComplain(@PathVariable Long id, @RequestBody ComplainDTO complainDTO) throws Exception {
        Complain updatedComplain = complainService.updateComplain(complainDTO);
        ComplainResponse complainResponse = ComplainResponse.fromComplain(updatedComplain);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Update complain successfully")
                .status(HttpStatus.OK)
                .data(complainResponse )
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteComplain(@PathVariable Long id) throws Exception {
        complainService.deleteComplain(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Delete complain successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/unfinished")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getUnfinishedComplains() throws Exception {
        List<Complain> complains = complainService.findUnfinishedComplains();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get unfinished complains successfully")
                .status(HttpStatus.OK)
                .data(complains)
                .build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> createComplain(@RequestBody ComplainDTO complainDTO) throws Exception {
        System.out.println("complainDTO: " + complainDTO);
        Complain newComplain = complainService.createComplain(complainDTO);
        ComplainResponse complainResponse = ComplainResponse.fromComplain(newComplain);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Create complain successfully")
                .status(HttpStatus.CREATED)
                .data(complainResponse)
                .build());
    }

}
