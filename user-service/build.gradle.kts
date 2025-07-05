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
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.cloud.eureka.client)
    implementation(libs.spring.kafka)
    implementation(libs.spring.boot.starter.jpa)
    implementation(libs.liquibase)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    compileOnly(libs.lombok)
    runtimeOnly(libs.postgresql)
    annotationProcessor(libs.lombok)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.kafka.test)

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
    }
}
