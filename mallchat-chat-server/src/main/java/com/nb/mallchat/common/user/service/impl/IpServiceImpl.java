package com.nb.mallchat.common.user.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.dto.IpResult;
import com.nb.mallchat.common.user.domain.entity.IpDetail;
import com.nb.mallchat.common.user.domain.entity.IpInfo;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 */
@Slf4j
public class IpServiceImpl implements IpService {
	@Autowired
	private UserDao userDao;
	private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>(500),
			new NamedThreadFactory("refresh-ipDetail",false));
	@Override
	public void refreshIpDetailAsync(Long uid) {
		EXECUTOR.execute(() ->{
			User user = userDao.getById(uid);
			IpInfo ipInfo = user.getIpInfo();
			if(Objects.isNull(ipInfo)){
				return;
			}
			String ip = ipInfo.needRefreshIp();
			if(StringUtils.isBlank(ip)){
				return;
			}
			IpDetail ipDetail = tryGetIpDetailOrNullTreeTimes(ip);
			if(Objects.nonNull(ipDetail)){
				ipInfo.refreshIpDetail(ipDetail);
				User user1 = new User();
				user1.setId(uid);
				user1.setIpInfo(ipInfo);
				userDao.updateById(user1);
			}
		});
	}

	private IpDetail tryGetIpDetailOrNullTreeTimes(String ip) {
		for (int i = 0; i < 3; i++) {
			IpDetail ipDetailOrNull = getIpDetailOrNull(ip);
			if(Objects.nonNull(ipDetailOrNull)){
				return ipDetailOrNull;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.error("tryGetIpDetailOrNullTreeTimes InterrruptedException", e);
			}
		}
		return null;
	}

	public static IpDetail getIpDetailOrNull(String ip) {
		String body = HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
		try {
			IpResult<IpDetail> result = JSONUtil.toBean(body, new TypeReference<IpResult<IpDetail>>() {}, false);
			if (result.isSuccess()) {
				return result.getData();
			}
		} catch (Exception ignored) {
		}
		return null;
	}
}
