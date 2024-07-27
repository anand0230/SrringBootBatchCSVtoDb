package com.nt.processor;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.Exam_Result;
@Component
public class EmployeeInfoItemProcessor implements ItemProcessor<Exam_Result,Exam_Result>{

	@Override
	public Exam_Result process(Exam_Result item) throws Exception {
		
		if(item.getPercentage()>=90) {
			return item;
		}else {
			return null;
		}
	}

	
	
}
