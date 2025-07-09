plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

dependencies {
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.mapstruct)
	annotationProcessor(libs.mapstruct.processor)
	implementation(libs.jjwt.api)
	runtimeOnly(libs.jjwt.jackson)
	runtimeOnly(libs.jjwt.impl)
}



