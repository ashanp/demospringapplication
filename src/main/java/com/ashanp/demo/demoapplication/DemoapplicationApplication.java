package com.ashanp.demo.demoapplication;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableBatchProcessing
public class DemoapplicationApplication {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step stepone(){
		return this.stepBuilderFactory.get("stepone").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String parameter = chunkContext.getStepContext().getJobParameters().get("time").toString();
				System.out.println("---->Step 1 and the parameter is: "+ parameter);
				return RepeatStatus.FINISHED;
			}
		}).build();
	}


	@Bean
	public Step steptwo(){
		return this.stepBuilderFactory.get("steptwo").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String parameter = chunkContext.getStepContext().getJobParameters().get("time").toString();
				System.out.println("---->Step 2 and the parameter is: "+ parameter);

				return RepeatStatus.FINISHED;
			}
		}).build();
	}



	@Bean
	public Step stepthree(){
		return this.stepBuilderFactory.get("stepthree").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String parameter = chunkContext.getStepContext().getJobParameters().get("time").toString();
				System.out.println("---->Step 3 and the parameter is: "+ parameter);
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step stepfour(){
		return this.stepBuilderFactory.get("stepfour").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String parameter = chunkContext.getStepContext().getJobParameters().get("time").toString();
				System.out.println("---->Step 4 and the parameter is: "+ parameter);
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Job deliverPackageJob(){
		return this.jobBuilderFactory.get("deliverPackageJob")
		.start(stepone())
		.next(steptwo())
		.on("FAILED").to(stepfour())
		.from(steptwo())
		.on("*").to(stepthree())
		.next(stepfour())
		.end()
		.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoapplicationApplication.class, "time="+ System.currentTimeMillis());
	}

}