package com.example.server.controller;

import javax.validation.Valid;

import com.example.server.constant.Constant;
import com.example.server.entity.Comment;
import com.example.server.model.request.CommentSearchRequest;
import com.example.server.model.request.CreateComment;
import com.example.server.model.response.CommentLastPageResponse;
import com.example.server.model.response.CommentResponse;
import com.example.server.service.CommentService;
import com.example.server.util.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/Comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ResponseUtils responseUtils;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateComment comment){
        try{
            Comment createComment = commentService.saveComment(comment);
            if (createComment == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Create comment failed", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Create comment successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE,"Create comment fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> deleteComment(@PathVariable(name="id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id", HttpStatus.BAD_REQUEST);
            }
            Boolean is_deleted = commentService.deleteComment(id);
            if(is_deleted == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Delete comment fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity("NULL", Constant.SUCCESS, "Delete comment successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("Null", Constant.FAILURE, "Delete comment fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(consumes = {"test/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> updateComment(@RequestBody CreateComment commentDto) {
        try{
            if (commentDto.getId() == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id", HttpStatus.BAD_REQUEST);
            }
            Boolean comment = commentService.updateComment(commentDto);
            if(comment == false){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail", HttpStatus.BAD_REQUEST);
            }
            return responseUtils.getResponseEntity(comment, Constant.SUCCESS, "Update comment successfully", HttpStatus.OK);
        }
        catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Update comment fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/filter")
    public ResponseEntity<?> showCommentBySearch(@RequestBody CommentSearchRequest commentSearchRequest){
        try{
            if (commentSearchRequest.getLimit() < 0 || commentSearchRequest.getPage() < 0){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Limit must larger or equal 0 and page must larger than 0", HttpStatus.BAD_REQUEST);
            }
            CommentLastPageResponse comment = commentService.searchCommentByUser(commentSearchRequest);
            if (comment == null){
                return responseUtils.getResponseEntity(comment, Constant.SUCCESS, "Don't have comment", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(comment.getList(), Constant.SUCCESS, "Show comment success", comment.getLastPage(), HttpStatus.OK);
        } catch(Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Show comment failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", consumes = {"text/plain", "application/*"}, produces = "application/json")
    public ResponseEntity<?> getComment(@PathVariable(name = "id") Long id){
        try{
            if(id == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Must has comment id", HttpStatus.BAD_REQUEST);
            }
            CommentResponse commentResponse = commentService.findCommentById(id);
            if (commentResponse == null){
                return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get comment fail", HttpStatus.OK);
            }
            return responseUtils.getResponseEntity(commentResponse, Constant.SUCCESS, "Get comment successfully", HttpStatus.OK);
        } catch (Exception e){
            return responseUtils.getResponseEntity("NULL", Constant.FAILURE, "Get comment fail", HttpStatus.BAD_REQUEST);
        }
    }
}
