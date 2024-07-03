package com.ttasjwi.oauth2

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate


fun main() {
    val path = "C:\\Users\\ttasjwi\\projects\\spring\\spring-security-oauth2\\app\\src\\main\\resources\\certs"
    val file = File("${path}\\publicKey.txt")

    val inputStream = FileInputStream("${path}\\apiKey.jks")
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
    keyStore.load(inputStream, "pass1234".toCharArray())

    val alias = "apiKey"
    val key = keyStore.getKey(alias, "pass1234".toCharArray())

    if (key is PrivateKey) {
        val certificate: Certificate = keyStore.getCertificate(alias)
        val publicKey = certificate.publicKey

        if (!file.exists()) {
            var publicKeyString = java.util.Base64.getMimeEncoder().encodeToString(publicKey.encoded)
            publicKeyString = "-----BEGIN PUBLIC KEY-----\r\n$publicKeyString\r\n-----END PUBLIC KEY-----"

            val writer = OutputStreamWriter(FileOutputStream(file), Charset.defaultCharset())
            writer.write(publicKeyString)
            writer.close()
        }
        inputStream.close()
    }
}
