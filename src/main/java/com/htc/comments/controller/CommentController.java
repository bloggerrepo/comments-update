package com.htc.comments.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htc.comments.CustomException.ResourceNotFoundException;
import com.htc.comments.entity.Comment;
import com.htc.comments.repo.CommentRepository;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	CommentRepository commentRepository;

	@PostMapping("/comments")
	public Comment createComment(@RequestBody Comment comment) {
		if (comment.getCreatedAt() == null)
			comment.setCreatedAt(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	@PutMapping("/comments/{id}")
	public Comment updateComment(@PathVariable(value = "id") Long commentId, @RequestBody Comment commentDetails) {

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		comment.setPostName(commentDetails.getPostName());
		comment.setComment(commentDetails.getComment());
		comment.setUpdatedAt(LocalDateTime.now());

		Comment updatedComment = commentRepository.save(comment);
		return updatedComment;
	}

	/*
	 * @GetMapping("/get") public Comment dummyComment() { Comment comment = new
	 * Comment(); comment.setId(3L); comment.setPostName("Java");
	 * comment.setComment("Practice Everyday");
	 * comment.setCreatedAt(LocalDateTime.now());
	 * comment.setUpdatedAt(LocalDateTime.now());
	 * 
	 * return comment;
	 * 
	 * }
	 */

}
