dependencies {
    // web
    implementation(Dependencies.SPRING_WEB)

    // security
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.SPRING_OAUTH2_RESOURCE_SERVER)

    // common
    implementation(project(":common"))

    // logging
    implementation(project(":support:logging"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
