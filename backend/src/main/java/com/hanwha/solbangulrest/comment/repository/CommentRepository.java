package com.hanwha.solbangulrest.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select c from Comment c join fetch c.author where c.post.id = :postId order by c.createdDateTime asc")
	List<Comment> findByPostId(Long postId);
}
