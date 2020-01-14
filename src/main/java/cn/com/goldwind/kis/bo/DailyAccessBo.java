package cn.com.goldwind.kis.bo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author alvin
 *
 */
@Data
public class DailyAccessBo implements Serializable {


	private static final long serialVersionUID = 7602099469933218545L;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date accessDate;

	private Integer accessCount;
	
	private Integer accessEmpCount;

}
