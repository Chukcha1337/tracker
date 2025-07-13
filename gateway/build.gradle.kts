plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

dependencies {
	implementation(project(":core"))
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.cloud.config.client)
	implementation(libs.spring.cloud.starter.gateway.webflux)
	implementation(libs.springdoc.starter.webflux.ui)
	implementation(libs.spring.cloud.eureka.client)
	implementation(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.spring.boot.starter.data.redis.reactive)
	implementation(libs.commons.codec)

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
	}
}
