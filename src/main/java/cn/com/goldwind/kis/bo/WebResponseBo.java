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
public class WebResponseBo implements Serializable {

	private static final long serialVersionUID = 447578855328514581L;

	private String empNO;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date responseTime;

	private String userName;

	private String email;

	private String unitTxt;

	private String centerTxt;

	private String deptName;

	private String zhrOtext;

}
