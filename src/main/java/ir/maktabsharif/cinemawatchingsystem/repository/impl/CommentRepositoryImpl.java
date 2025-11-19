package ir.maktabsharif.cinemawatchingsystem.repository.impl;

import ir.maktabsharif.cinemawatchingsystem.model.Comment;
import ir.maktabsharif.cinemawatchingsystem.repository.CommentRepository;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Comment, Long> implements CommentRepository {

    public CommentRepositoryImpl() {
        super(Comment.class);
    }
    @Override
    public Class<Comment> getModelClass() {
        return Comment.class;
    }
}
