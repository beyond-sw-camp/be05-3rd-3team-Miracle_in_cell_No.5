package com.hanwha.solbangulrest.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.comment.domain.Comment;
import com.hanwha.solbangulrest.comment.dto.CommentResponseDto;
import com.hanwha.solbangulrest.comment.dto.CommentSaveDto;
import com.hanwha.solbangulrest.comment.repository.CommentRepository;
import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.post.repository.PostRepository;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(CommentSaveDto commentSaveDto) {
		Long postId = commentSaveDto.getPostId();
		Long userId = commentSaveDto.getUserId();

		Post post = postRepository.findById(postId).orElseThrow(
			() -> new IllegalArgumentException("해당 post가 없습니다. id=" + postId));
		User author = userRepository.findById(userId).orElseThrow(
			() -> new IllegalArgumentException("해당 user가 없습니다. id=" + userId));

		Comment comment = commentSaveDto.toEntity(post, author);
		post.addComment(comment);
		commentRepository.save(comment);

		return comment.getId();
	}

	@Transactional
	public void delete(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("해당 comment가 없습니다. id=" + id));
		Post post = comment.getPost();
		post.removeComment(comment);
		commentRepository.delete(comment);
	}

	public List<CommentResponseDto> findByPostId(Long postId) {
		return commentRepository.findByPostId(postId)
			.stream().map(CommentResponseDto::new)
			.toList();
	}
}
