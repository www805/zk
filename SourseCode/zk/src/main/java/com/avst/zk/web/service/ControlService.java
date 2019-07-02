package com.avst.zk.web.service;

import com.avst.zk.common.param.ControlInfoParam;
import com.avst.zk.common.util.baseaction.BaseAction;
import com.avst.zk.common.util.baseaction.Page;
import com.avst.zk.common.util.baseaction.RResult;
import com.avst.zk.common.util.baseaction.ReqParam;
import com.avst.zk.common.vo.ControlInfoParamVO;
import com.avst.zk.outside.interfacetoout.cache.ControlCache;
import com.avst.zk.web.vo.ControlVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ControlService extends BaseAction {

    /**
     * 清空断开的服务器缓存
     * @param result
     */
    public void clearControlInfo(RResult result) {

        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");
        ControlInfoParamVO paramVO = null;

        if (null != list && list.size() > 0) {
            Integer numSize = list.size();

            Iterator<ControlInfoParamVO> it = list.iterator();
            while (it.hasNext()) {

                paramVO = it.next();

                System.out.println(paramVO.getServername() + "  " + paramVO.getStatus());
                if (null != paramVO && null != paramVO.getStatus() && paramVO.getStatus() != 1) {
                    it.remove();
                }
            }
        }
        changeResultToSuccess(result);
    }


    public void getControlInfo(RResult result, ReqParam<ControlInfoParam> param) {
        ControlVO controlVO = new ControlVO();

        //读取定时器存入缓存的数据
        List<ControlInfoParamVO> list = ControlCache.getControlInfoList("list");

        ControlInfoParam paramParam = param.getParam();

        List<ControlInfoParamVO> listVO = new ArrayList<>();

        if (null != list && list.size() > 0) {

            Integer num = list.size();

            if (null == paramParam) {
                paramParam = new ControlInfoParam();
            }

            ControlInfoParam paramParamTwo = paramParam;
            paramParamTwo.setRecordCount(num);

            if (null == num || num < 1) {
                result.setData(list);
                paramParamTwo.setPageCount(0);
            } else {
                if (num % paramParam.getPageSize() == 0) {
                    paramParamTwo.setPageCount(num
                            / paramParam.getPageSize());
                } else {
                    paramParamTwo.setPageCount(num
                            / paramParam.getPageSize() + 1);
                }

                Integer tiao = paramParamTwo.getCurrPage() * paramParamTwo.getPageSize();
                Integer wei = tiao - paramParamTwo.getPageSize();

                for (int i = 0; i < list.size(); i++) {
                    ControlInfoParamVO infoParamVO = list.get(i);
                    if (i >= wei && i < tiao) {
                        listVO.add(infoParamVO);
                    }
                }
            }
            controlVO.setPageparam(paramParamTwo);
            controlVO.setPagelist(listVO);

            result.setData(controlVO);

        }
        changeResultToSuccess(result);
    }
}
