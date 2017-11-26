package com.linecorp.springreact.config

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.resource.GzipResourceResolver
import org.springframework.web.servlet.resource.PathResourceResolver

class SpringReactConfigurer : WebMvcConfigurerAdapter() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry?.addResourceHandler("/js/**")
                ?.addResourceLocations("classpath:/static/js/")
                ?.resourceChain(true)
                ?.addResolver(PathResourceResolver())
                ?.addResolver(GzipResourceResolver())
    }
}
