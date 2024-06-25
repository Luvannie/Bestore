package com.luvannie.springbootbookecommerce.controller;

import com.luvannie.springbootbookecommerce.component.SecurityComponent;
import com.luvannie.springbootbookecommerce.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class CommentController {
    private final CommentService commentService;
    private final SecurityComponent securityComponent;

//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getAllComments(
//            @RequestParam(value = "user_id", required = false) Long userId,
//            @RequestParam("book_id") Long bookId
//    ) {
//        List<CommentResponse> commentResponses;
//        if (userId == null) {
//            commentResponses = commentService.getCommentsByProduct(bookId);
//        } else {
//            commentResponses = commentService.getCommentsByUserAndProduct(userId, bookId);
//        }
//        return ResponseEntity.ok().body(ResponseObject.builder()
//                .message("Get comments successfully")
//                .status(HttpStatus.OK)
//                .data(commentResponses)
//                .build());
//    }
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//    public ResponseEntity<ResponseObject> updateComment(
//            @PathVariable("id") Long commentId,
//            @RequestBody CommentDTO commentDTO
//    ) throws Exception {
//        User loginUser = securityUtils.getLoggedInUser();
//        if (!Objects.equals(loginUser.getId(), commentDTO.getUserId())) {
//            return ResponseEntity.badRequest().body(
//                    new ResponseObject(
//                            "You cannot update another user's comment",
//                            HttpStatus.BAD_REQUEST,
//                            null));
//
//        }
//        commentService.updateComment(commentId, commentDTO);
//        return ResponseEntity.ok(
//                new ResponseObject(
//                        "Update comment successfully",
//                        HttpStatus.OK, null));
//    }
//    @PostMapping("")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//    public ResponseEntity<ResponseObject> insertComment(
//             @RequestBody CommentDTO commentDTO
//    ) {
//        // Insert the new comment
//        User loginUser = securityUtils.getLoggedInUser();
//        if(loginUser.getId() != commentDTO.getUserId()) {
//            return ResponseEntity.badRequest().body(
//                    new ResponseObject(
//                            "You cannot comment as another user",
//                            HttpStatus.BAD_REQUEST,
//                            null));
//        }
//        commentService.insertComment(commentDTO);
//        return ResponseEntity.ok(
//                ResponseObject.builder()
//                        .message("Insert comment successfully")
//                        .status(HttpStatus.OK)
//                        .build());
//    }
//    @PostMapping("/generateFakeComments")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<ResponseObject> generateFakeComments() throws Exception {
//        commentService.generateFakeComments();
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Insert fake comments succcessfully")
//                .data(null)
//                .status(HttpStatus.OK)
//                .build());
//    }
}
