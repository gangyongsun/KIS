package cn.com.goldwind.kis.bo;

import java.io.Serializable;

import lombok.Data;

@Data
/**
 * 
 * @author alvin
 *
 */
public class UserNameBo implements Serializable {

	private static final long serialVersionUID = -3517036413915371245L;

	private String userName;

	private Integer accessCount;

}
