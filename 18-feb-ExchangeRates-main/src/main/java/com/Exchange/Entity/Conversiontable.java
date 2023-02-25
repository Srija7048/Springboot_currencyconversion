package com.Exchange.Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="audit_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conversiontable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long request_id;
	String status;
	String request;
	String Reponse;
	Timestamp create_ts;
	Timestamp update_ts;
}
