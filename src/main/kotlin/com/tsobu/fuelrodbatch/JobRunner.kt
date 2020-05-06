package com.tsobu.fuelrodbatch

import org.springframework.batch.core.*
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.JobOperator
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@EnableScheduling
@Component
class JobRunner(
        @Autowired
        private val job: Job,
        @Autowired
        private val jobLauncher: JobLauncher
) {

//     @Scheduled(fixedRate = 5000, initialDelay = 2000)
    @Scheduled(cron = "* */2 * * * *")
    fun findAndRunJob() {

        try {
            val jobParameters = JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters()

            val execution = jobLauncher.run(job, jobParameters)

            println("Exit status : " + execution.status);
        } catch (e: JobInstanceAlreadyCompleteException) {
            e.printStackTrace()
        } catch (e: JobExecutionAlreadyRunningException) {
            e.printStackTrace()
        } catch (e: JobParametersInvalidException) {
            e.printStackTrace()
        } catch (e: JobRestartException) {
            e.printStackTrace()
        }
    }
}