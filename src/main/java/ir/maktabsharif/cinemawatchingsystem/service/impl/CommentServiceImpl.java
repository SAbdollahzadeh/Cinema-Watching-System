package ir.maktabsharif.cinemawatchingsystem.service.impl;

import ir.maktabsharif.cinemawatchingsystem.model.Comment;
import ir.maktabsharif.cinemawatchingsystem.repository.BaseRepository;
import ir.maktabsharif.cinemawatchingsystem.repository.CommentRepository;
import ir.maktabsharif.cinemawatchingsystem.service.CommentService;

public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {
    CommentRepository commentRepository;
    public CommentServiceImpl(CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }
}
