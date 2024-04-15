package com.hanwha.solbangulrest.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.room.domain.Room;
import com.hanwha.solbangulrest.user.domain.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	@Query("select count(p) > 0 "
		+ "from Post p "
		+ "where p.author = :author "
		+ "and p.room = :room "
		+ "and p.category = :category "
		+ "and day(p.createdDate) = day(current date)")
	boolean existsByLastPost(@Param("author") User author, @Param("room") Room room,
		@Param("category") Category category);

	@Query("select p from Post p join fetch p.author join fetch p.room where p.id = :id")
	Optional<Post> findByIdWithAuthorAndRoom(Long id);

	@Query("select p from Post p join fetch p.author join fetch p.room")
	List<Post> findAllWithAuthorAndRoom();

	Page<Post> findAll(Specification<Post> spec, Pageable pageable);

	@Query("select p from Post p where p.room.id = :roomId")
	Page<Post> findPostsByRoomIdPaging(@Param("roomId") Long roomId, Pageable pageable);
}
