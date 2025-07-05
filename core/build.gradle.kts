plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

dependencies {
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.lombok)
	implementation(libs.spring.boot.starter.security)
	annotationProcessor(libs.lombok)
	implementation(libs.mapstruct)
	annotationProcessor(libs.mapstruct.processor)
}

