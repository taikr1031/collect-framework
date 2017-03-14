package com.xxx.collect.core.util.log;

import org.apache.log4j.Logger;

public class LogCatalog {
	public static Logger sql = Logger.getLogger("astar.sql");
	public static Logger dao_UDI = Logger.getLogger("astar.dao_UDI");
	public static Logger app = Logger.getLogger("astar.app");
	//http
	//public static Logger httpException = Logger.getLogger("astar.httpException");
	public static Logger httpProxy = Logger.getLogger("astar.httpProxy");
	public static Logger httpProxyCheckThread = Logger.getLogger("astar.httpProxyCheckThread");
	public static Logger httpLog = Logger.getLogger("astar.httpLog");
	
	public static Logger baiduSimilar = Logger.getLogger("astar.baiduSimilar");
	
	public static Logger baseTaskManager = Logger.getLogger("astar.baseTaskManager");
	public static Logger dbTaskManager = Logger.getLogger("astar.dbTaskManager");
	public static Logger spiderTaskLogThread = Logger.getLogger("astar.spiderTaskLogThread");
	public static Logger spiderTask = Logger.getLogger("astar.spiderTask");
	
	/* ------ҵ����־-------- */
	public static Logger biz_gameResc = Logger.getLogger("astar.biz.gameResc");
	public static Logger biz_opensource = Logger.getLogger("astar.biz.opensource");
	public static Logger biz_zhi = Logger.getLogger("astar.biz.zhi");
	public static Logger biz_zhihu = Logger.getLogger("astar.biz.zhihu");
	public static Logger biz_genClassList = Logger.getLogger("astar.biz.genClassList");
	public static Logger biz_genUserList = Logger.getLogger("astar.biz.genUserList");
	public static Logger biz_genUserInfo = Logger.getLogger("astar.biz.genUserInfo");
	public static Logger biz_dian = Logger.getLogger("astar.biz.dian");
	public static Logger biz_xinzhi = Logger.getLogger("astar.biz.xinzhi");
	
}
