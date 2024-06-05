dependencies {
    // web
    implementation(Dependencies.SPRING_WEB)

    // security
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.SPRING_OAUTH2_CLIENT)

    // thymeleaf
    implementation(Dependencies.THYMELEAF)
    implementation(Dependencies.THYMELEAF_EXTRAS_SECURITY)

    // logging
    implementation(project(":support:logging"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
