package io.jeidiiy.outflearn.course.domain;

import io.jeidiiy.outflearn.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User creator;

	@Builder
	public Course(Long id, String title, String description, User creator) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.creator = creator;
	}

	public static Course from(CourseCreate courseCreate, User creator) {
		return Course.builder()
			.title(courseCreate.getTitle())
			.description(courseCreate.getDescription())
			.creator(creator)
			.build();
	}
}
