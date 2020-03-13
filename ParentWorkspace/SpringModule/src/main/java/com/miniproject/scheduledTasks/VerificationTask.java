package com.miniproject.scheduledTasks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.miniproject.model.Verification;
import com.miniproject.service.VerificationService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;

@Component
public class VerificationTask implements ScheduledTask {

	@Autowired
	VerificationService verificationService;

	@Scheduled(cron = "0 0 * * * *")
	@Override
	public void execute() {
		LogUtil.getLogger().info("Scheduled Task Executed : " + new Date());
		List<Verification> verificationList = verificationService.getAllVerifications();
		if (!CollectionUtils.isEmpty(verificationList)) {
			verificationList.stream().forEach(item -> {
				if (checkTimeLapsed(item)) {
					LogUtil.getLogger().info("Verification task removed : " + item.getId());
					verificationService.removeVerification(item.getId());
				}
			});
		}
	}

	public boolean checkTimeLapsed(Verification verify) {
		Instant taskStartInstant = verify.getTimeCreated().toInstant();
		Instant maxVerificationTimeLimit = Instant.now().minus(CommonUtil.maxVerificationTime, ChronoUnit.HOURS);
		if (taskStartInstant.isBefore(maxVerificationTimeLimit)) {
			return true;
		}
		return false;
	}
}
