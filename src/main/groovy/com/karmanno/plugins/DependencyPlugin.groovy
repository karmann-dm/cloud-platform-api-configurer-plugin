package com.karmanno.plugins

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.internal.dsl.StandardDependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class DependencyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply('java')
        project.plugins.apply('maven-publish')
        project.plugins.apply(SpringBootPlugin)
        project.plugins.apply(DependencyManagementPlugin)

        project.extensions.getByType(
                StandardDependencyManagementExtension
        ).with {
            imports {
                mavenBom "org.springframework.cloud:spring-cloud-starter-parent:Greenwich.SR4"
            }
        }

        project.dependencies.add('implementation', 'org.springframework.boot:spring-boot-starter-data-mongodb-reactive')
        project.dependencies.add('implementation', 'org.springframework.boot:spring-boot-starter-webflux')
        project.dependencies.add('implementation', 'org.springframework.cloud:spring-cloud-starter-openfeign')

        project.dependencies.add('compile', 'org.projectlombok:lombok')
        project.dependencies.add('annotationProcessor', 'org.projectlombok:lombok')

        project.dependencies.add('testImplementation', 'org.springframework.boot:spring-boot-starter-test')
        project.dependencies.add('testImplementation', 'io.projectreactor:reactor-test')
        project.dependencies.add('testImplementation', 'com.github.tomakehurst:wiremock-jre8:2.25.1')
        project.dependencies.add('testImplementation', 'de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0')
    }
}
