package com.avst.zk.common.util.baseaction;

import com.avst.zk.common.util.DateUtil;
import org.springframework.util.StringUtils;

/**
 * @author wb
 *
 */
public class BaseAction {
   
    
	public RResult createNewResultOfFail() {
		RResult rresult = new RResult();
		rresult.setActioncode(Code.FAIL.toString());//
		rresult.setMessage("请求失败");
		// rresult.setActioncode(Code.SUCCESS.toString());//暂时给它全部成功，不可取的逻辑
		rresult.setVersion(Version.V1);
		rresult.setData("");
		rresult.setEndtime(DateUtil.getDateAndMinute());
		return rresult;
	}

	/**
	 * 赋值上一个请求的页面id
	 * @return
	 */
	public RResult createNewResultOfFail(String pageid) {
		RResult rresult = createNewResultOfFail();
		rresult.setNextpageid(pageid);
		return rresult;
	}

	/** @param rdatas */
	protected final <T> void changeResultToSuccess(RResult rresult, T rdatas,
			String message) {
		rresult.setData(rdatas);
		rresult.setEndtime(DateUtil.getDateAndMinute());
		changeResultToSuccess(rresult, message);
	}

	protected final <T> void changeResultToSuccess(RResult rresult, T rdatas) {
		changeResultToSuccess(rresult, rdatas, null);
	}

	protected final <T> void changeResultToSuccess(RResult rresult,
			String message) {
		if (StringUtils.isEmpty(message)) {
			message = "成功请求";
		}
		rresult.setMessage(message);
		changeResultToSuccess(rresult);
	}

	public final <T> void changeResultToSuccess(RResult rresult) {
		if (null == rresult) {
			rresult = createNewResultOfFail();
		}
		rresult.setActioncode(Code.SUCCESS.toString());
		rresult.setMessage("请求成功");
		//System.out.println(JacksonUtil.objebtToString(rresult));
	}

	

	


}
