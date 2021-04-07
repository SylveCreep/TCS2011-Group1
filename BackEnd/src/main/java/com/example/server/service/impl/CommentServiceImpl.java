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
import com.example.server.model.request.CommentSearchRequest;
import com.example.server.model.request.CreateComment;
import com.example.server.model.request.PagingRequest;
import com.example.server.model.response.CommentLastPageResponse;
import com.example.server.model.response.CommentResponse;
import com.example.server.service.CommentService;
import com.example.server.service.CommonService;
import com.example.server.service.UserService;
import com.example.server.util.QueryCheck;
import com.example.server.util.ResponseUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {
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

    @Override
    public List<Comment> findAll() {
        List<Comment> list = new ArrayList<>();
        commentDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public CommentResponse findCommentById(Long id) {
        CommentResponse commentResponse = commentDao.findCommentById(id);
        return commentResponse;
    }

    @Override
    public Boolean deleteComment(Long id) {
        try {
            Comment comment = commentDao.getOne(id);
            comment.setIs_deleted(Constant.DELETED);
            commentDao.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateComment(CreateComment commentDto) {
        try {
            Comment comment = commentDao.getOne(commentDto.getId());
            User user = userDao.findById(commentDto.getUserId()).get();
            Contribution contribution = contributionDao.getOne(commentDto.getContributionId());
            if (user == null) {
                return false;
            }
            if (contribution == null) {
                return false;
            }
            comment.setContent(commentDto.getContent());
            comment.setContribution(contribution);
            comment.setUser(user);
            commentDao.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Comment saveComment(CommentDto comment) {
        Comment nComment = comment.getCommentFromDto();
        nComment.setContent(comment.getContent());
        nComment.setContribution(comment.getContribution());
        nComment.setUser(comment.getUser());
        nComment.setCode("C" + String.format("%04d", queryCheck.GetHighestId("comment")));
        return commentDao.save(nComment);
    }

    @Override
    public Comment saveComment(CreateComment comment) {
        try {
            Comment nComment = new Comment();
            nComment.setCode("C" + String.format("%04d", queryCheck.GetHighestId("comment")));
            nComment.setContent(comment.getContent());
            Optional<User> user = userDao.findById(comment.getUserId());
            if (comment.getParentId() != null) {
                Comment parentComment = commentDao.findExistedCommentById(comment.getParentId());
                nComment.setParentComment(parentComment);
            }
            Contribution contribution = contributionDao.getOne(comment.getContributionId());
            if (user.get() == null || user.get().getIs_deleted() == Constant.DELETED) {
                return null;
            }
            if (contribution == null || contribution.getIs_deleted() == Constant.DELETED) {
                return null;
            }
            nComment.setUser(user.get());
            nComment.setContribution(contribution);
            return commentDao.save(nComment);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CommentLastPageResponse searchCommentByUser(CommentSearchRequest commentSearchRequest) {
        try {
            int offset = commentSearchRequest.getPage() - 1;
            Sort sort = responseUtil.getSortObj(commentSearchRequest);
            Page<Comment> list = commentDao.searchCommentByUser(commentSearchRequest.getId(),
                    commentSearchRequest.getUserId(), commentSearchRequest.getCode(),
                    PageRequest.of(offset, commentSearchRequest.getLimit(), sort));

            int lastPage = Math.round(list.getTotalElements() / commentSearchRequest.getLimit()
                    + ((list.getTotalElements() % commentSearchRequest.getLimit() == 0) ? 0 : 1));
            CommentLastPageResponse object = new CommentLastPageResponse();
            List<CommentResponse> listResponse = new ArrayList<>();
            for (Comment comment : list) {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setId(comment.getId());
                commentResponse.setContent(comment.getContent() == null ? "" : comment.getContent());
                commentResponse.setCode(comment.getCode() == null ? "" : comment.getCode());
                commentResponse.setContributionId(
                        comment.getContribution() == null ? null : comment.getContribution().getId());
                commentResponse.setUserId(comment.getUser() == null ? null : comment.getUser().getId());
                commentResponse.setUserName(comment.getUser() == null ? null : comment.getUser().getFullName());
                listResponse.add(commentResponse);
            }
            object.setLastPage(lastPage);
            object.setList(listResponse);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> getCommentListResponse(PagingRequest pagingRequest) {
        try {
            Sort sort = responseUtil.getSortObj(pagingRequest);
            Page<Comment> list = commentDao
                    .getNonDelRole(PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort));
            int lastPage = Math.round(list.getTotalElements() / pagingRequest.getLimit()
                    + ((list.getTotalElements() % pagingRequest.getLimit() == 0) ? 0 : 1));
            List<Object> object = new ArrayList<>();
            List<CommentResponse> listResponse = new ArrayList<>();
            for (Comment comment : list) {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setId(comment.getId());
                commentResponse.setContent(comment.getContent() == null ? "" : comment.getContent());
                commentResponse.setCode(comment.getCode() == null ? "" : comment.getCode());
                commentResponse.setContributionId(
                        comment.getContribution() == null ? null : comment.getContribution().getId());
                commentResponse.setUserId(comment.getUser() == null ? null : comment.getUser().getId());
                commentResponse
                        .setParentId(comment.getParentComment() == null ? null : comment.getParentComment().getId());
                commentResponse.setUserName(comment.getUser() == null ? null : comment.getUser().getFullName());
                listResponse.add(commentResponse);
            }
            object.add(listResponse);
            object.add(lastPage);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
