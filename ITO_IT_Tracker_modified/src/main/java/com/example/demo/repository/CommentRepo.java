package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comments;

public interface CommentRepo extends JpaRepository<Comments, Integer>{
	
	@Query(value="select * from comment where ticket_id = :reportedId",nativeQuery = true )
	public List<Comments> getCommentList(@Param("reportedId") int reportedId);

	
	
}
