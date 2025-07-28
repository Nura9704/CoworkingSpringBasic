plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework:spring-context:6.2.7")
    implementation("org.springframework:spring-orm:6.2.7")
    implementation("org.springframework:spring-tx:6.2.7")

    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.2.7.Final")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.7.7")

    implementation("org.springframework:spring-webmvc:6.2.7")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("javax.servlet:jstl:1.2") // JSTL
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
