package com.gl05.bad;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.gl05.bad.web.Backup;

@SpringBootApplication
@EnableWebSocketMessageBroker
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class SistemaDecatur {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDecatur.class, args);
	}

	// Configurar la tarea programada utilizando la anotación @Scheduled
    @Scheduled(cron = "0 0 17 * * ?") // Ejecutar diariamente a las 5:00 PM
    public void realizarCopiaDeSeguridad() throws JobExecutionException {
        Backup backupJob = new Backup();
        backupJob.execute(null);
    }

    // Configurar Quartz Scheduler
    @Bean
    public JobDetail backupJobDetail() {
        return JobBuilder.newJob(Backup.class)
                .withIdentity("backupJob", "backupGroup")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger backupJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(backupJobDetail())
                .withIdentity("backupTrigger", "backupGroup")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 0))
                .build();
    }

    @Bean
    public Scheduler scheduler(Trigger backupJobTrigger, JobDetail backupJobDetail) throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(backupJobDetail, backupJobTrigger);
        return scheduler;
    }
}
