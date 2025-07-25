plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation(project(":core"))
	implementation(project(":outbox"))
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.cloud.config.client)
	implementation(libs.spring.cloud.eureka.client)
	implementation(libs.springdoc.starter.webmvc.ui)
	implementation(libs.spring.kafka)
	implementation(libs.spring.boot.starter.jpa)
	implementation(libs.liquibase)
	implementation(libs.spring.boot.starter.validation)
	compileOnly(libs.lombok)
	implementation(libs.postgresql)
	annotationProcessor(libs.lombok)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.spring.kafka.test)

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
	}
}
