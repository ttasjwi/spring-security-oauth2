object Dependencies {

    // kotlin
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin"

    // spring
    const val SPRING_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"

    // web
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"
    const val THYMELEAF = "org.springframework.boot:spring-boot-starter-thymeleaf"
    const val THYMELEAF_EXTRAS_SECURITY = "org.thymeleaf.extras:thymeleaf-extras-springsecurity6"

    // security
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_OAUTH2_CLIENT = "org.springframework.boot:spring-boot-starter-oauth2-client"
    const val SPRING_OAUTH2_RESOURCE_SERVER = "org.springframework.boot:spring-boot-starter-oauth2-resource-server"
    const val SPRING_OAUTH2_AUTHORIZATION_SERVER = "org.springframework.boot:spring-boot-starter-oauth2-authorization-server"
    // session
    const val SPRING_SESSION_CORE = "org.springframework.session:spring-session-core:${DependencyVersions.SPRING_SESSION_CORE_VERSION}"

    const val NIMBUS_OAUTH2_OIDC_SDK = "com.nimbusds:oauth2-oidc-sdk:${DependencyVersions.NIMBUS_OAUTH2_OIDC_SDK}"

    // logger
    const val KOTLIN_LOGGING = "io.github.oshai:kotlin-logging:${DependencyVersions.KOTLIN_LOGGING_VERSION}"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${PluginVersions.SPRING_BOOT_VERSION}"
    const val SPRING_SECURITY_TEST = "org.springframework.security:spring-security-test"

}
