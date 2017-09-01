package cn.com.grean.protocol;

import java.util.List;

import cn.com.grean.model.HistoryDataFormat;

/**
 * 查询历史数据
 * @author Administrator
 *
 */
public interface ProtocolHistoryData {
	HistoryDataFormat onComplete(long from,long to);
	void onCompleteLog(long from,long to,List<String> list);
}
