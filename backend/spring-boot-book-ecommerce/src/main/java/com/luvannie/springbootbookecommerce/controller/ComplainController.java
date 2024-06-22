package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.dto.ComplainDTO;
import com.luvannie.springbootbookecommerce.entity.Complain;
import com.luvannie.springbootbookecommerce.responses.ResponseObject;
import com.luvannie.springbootbookecommerce.responses.complain.ComplainListResponse;
import com.luvannie.springbootbookecommerce.responses.complain.ComplainResponse;
import com.luvannie.springbootbookecommerce.service.complain.ComplainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
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

//    @GetMapping("/unfinished")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<ResponseObject> getUnfinishedComplains() throws Exception {
//        List<Complain> complains = complainService.findUnfinishedComplains();
//        return ResponseEntity.ok().body(ResponseObject.builder()
//                .message("Get unfinished complains successfully")
//                .status(HttpStatus.OK)
//                .data(complains)
//                .build());
//    }

    @GetMapping("/unfinished")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<ResponseObject> getUnfinishedComplains(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
) throws Exception {
    // Tạo Pageable từ thông tin trang và giới hạn
    PageRequest pageRequest = PageRequest.of(
            page, limit,
            Sort.by("id").ascending()
    );
    Page<ComplainResponse> complainPage = complainService.findUnfinishedComplains(pageRequest);
    // Lấy tổng số trang
    int totalPages = complainPage.getTotalPages();

    List<ComplainResponse> complainResponses = complainPage.getContent();



    // Add totalPages to each ComplainResponse object
    for (ComplainResponse complain : complainResponses) {
        complain.setTotalPages(totalPages);
    }
    ComplainListResponse complainListResponse = ComplainListResponse
            .builder()
            .complains(complainResponses)
            .totalPages(totalPages)
            .build();

    return ResponseEntity.ok().body(ResponseObject.builder()
            .message("Get unfinished complains successfully")
            .status(HttpStatus.OK)
            .data(complainListResponse)
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
