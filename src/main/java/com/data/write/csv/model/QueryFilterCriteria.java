package com.data.write.csv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @Getter @Setter
@EqualsAndHashCode 
@ToString
@Builder
public class QueryFilterCriteria {
	
	public String query;

}
