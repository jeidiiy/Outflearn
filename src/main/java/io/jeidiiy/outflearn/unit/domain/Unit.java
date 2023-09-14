package io.jeidiiy.outflearn.unit.domain;

import io.jeidiiy.outflearn.section.domain.Section;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "units")
@Getter
@ToString
@NoArgsConstructor
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unit_id")
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column
	private String body;

	@Column(nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	private Section section;

	@Builder
	public Unit(Long id, String title, String body, String url, Section section) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.url = url;
		this.section = section;
	}
}
