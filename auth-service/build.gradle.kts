plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.kotlin.jvm)
}

dependencies {
	implementation(project(":core"))
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.cloud.eureka.client)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.cloud.open.feign)
	implementation(libs.jjwt.api)
	runtimeOnly(libs.jjwt.jackson)
	runtimeOnly(libs.jjwt.impl)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.mapstruct)
	annotationProcessor(libs.mapstruct.processor)
	testImplementation(libs.spring.boot.starter.test)
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
	}
}

