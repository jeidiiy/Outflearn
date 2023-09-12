package io.jeidiiy.outflearn.section.domain;

import io.jeidiiy.outflearn.course.domain.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sections")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	@Builder
	public Section(Long id, String title, Course course) {
		this.id = id;
		this.title = title;
		this.course = course;
	}
}
