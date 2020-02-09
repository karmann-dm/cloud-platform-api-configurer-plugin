package com.karmanno.plugins

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class DependencyPluginTest extends Specification {
    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'com.karmanno.plugins.dependency'
            }
            
            repositories {
                mavenCentral()
            }
        """
    }

    def "can successfully configure URL through extension and verify it"() {
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('dependencies')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('org.projectlombok:lombok -> 1.18.10')
        result.output.contains('org.springframework.boot:spring-boot-starter-data-mongodb-reactive -> 2.1.10.RELEASE')
        result.output.contains('org.springframework.cloud:spring-cloud-starter-openfeign -> 2.1.4.RELEASE')
        result.output.contains('org.springframework.boot:spring-boot-starter-reactor-netty:2.1.10.RELEASE')

        result.task(':dependencies').outcome == TaskOutcome.SUCCESS
    }
}
