plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

dependencies {
	implementation(project(":core"))
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.cloud.config.client)
	implementation(libs.spring.cloud.starter.gateway.server.mvc)
	implementation(libs.spring.cloud.eureka.client)
	implementation(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.commons.codec)

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
	}
}
