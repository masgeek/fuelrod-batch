package com.tsobu.fuelrodbatch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@EnableScheduling
@Component
class JobRunner {
    @Autowired
    private val jobLauncher: JobLauncher? = null

    @Autowired
    private val job: Job? = null

    @Scheduled(fixedRate = 5000)
    fun findAndRunJob() {
        val jobParameters = JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()

        val execution = jobLauncher!!.run(job!!, jobParameters)


        println("Exit status : " + execution.status);
    }
}