package com.ttasjwi.oauth2.support.logging

import com.ttasjwi.oauth2.support.logging.impl.DelegatingLogger

fun getLogger(clazz: Class<*>): Logger = DelegatingLogger(clazz)
