package com.nt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class Exam_Result {
	
	@Id
	private Integer id;
	private LocalDateTime dob;
	private Double percentage;
	private Integer semester;
	
	
	
}
