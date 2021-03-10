package com.example.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.server.constant.Constant;
import com.example.server.dao.CommentDao;
import com.example.server.dao.ContributionDao;
import com.example.server.dao.UserDao;
import com.example.server.dto.CommentDto;
import com.example.server.entity.Comment;
import com.example.server.entity.Contribution;
import com.example.server.entity.User;
import com.example.server.model.request.CreateComment;
import com.example.server.service.UserService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "commentService")
public class CommentService {
    @Autowired
    private ResponseUtils responseUtil;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QueryCheck queryCheck;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ContributionDao contributionDao;

    public List<Comment> findAll(){
        List<Comment> list = new ArrayList<>();
        commentDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public Boolean deleteComment(Long id){
        try {
            Comment comment = commentDao.getOne(id);
            comment.setIs_deleted(Constant.DELETED);
            commentDao.save(comment);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public Boolean updateComment(CreateComment commentDto){
        try{
            Comment comment = commentDao.getOne(commentDto.getId());
            User user = userDao.findById(commentDto.getUserId()).get();
            Contribution contribution = contributionDao.getOne(commentDto.getContributionId());
            if (user == null){
                return false;
            }
            if (contribution == null){
                return false;
            }
            comment.setContent(commentDto.getContent());
            comment.setContribution(contribution);
            comment.setUser(user);
            commentDao.save(comment);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Comment saveComment(CommentDto comment){
        Comment nComment = comment.getCommentFromDto();
        nComment.setContent(comment.getContent());
        nComment.setContribution(comment.getContribution());
        nComment.setUser(comment.getUser());
        nComment.setCode("C" + String.format("%04d", queryCheck.GetHighestId("comment")));
        return commentDao.save(nComment);
    }

    public Comment saveComment(CreateComment comment){
        try {
            Comment nComment = new Comment();
            nComment.setCode("C" + String.format("%04d", queryCheck.GetHighestId("comment")));
            nComment.setContent(comment.getContent());
            Optional<User> user = userDao.findById(comment.getUserId());
            Contribution contribution = contributionDao.getOne(comment.getContributionId());
            if (user.get() == null || user.get().getIs_deleted() == Constant.DELETED){
                return null;
            }
            if(contribution == null || contribution.getIs_deleted() == Constant.DELETED){
                return null;
            }
            nComment.setUser(user.get());
            nComment.setContribution(contribution);
            return commentDao.save(nComment);
        }catch(Exception e){
            return null;
        }
    }
}
