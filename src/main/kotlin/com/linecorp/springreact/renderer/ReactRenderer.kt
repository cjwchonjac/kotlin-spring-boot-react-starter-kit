package com.linecorp.springreact.renderer

import jdk.nashorn.api.scripting.NashornScriptEngine
import jdk.nashorn.api.scripting.NashornScriptEngineFactory
import org.apache.commons.pool2.BasePooledObjectFactory
import org.apache.commons.pool2.ObjectPool
import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.GenericObjectPool
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.annotation.PostConstruct
import javax.script.Invocable
import javax.script.ScriptException

@Component
open class ReactRenderer : BasePooledObjectFactory<Invocable>() {
    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @Value("\${application.renderEngine.minIdle:4}")
    private var minIdle: Int? = null

    @Value("\${application.renderEngine.maxIdle:4}")
    private var maxIdle: Int? = null

    @Value("\${application.renderEngine.maxTotal:4}")
    private var maxTotal: Int? = null

    private var engines: ObjectPool<Invocable>? = null

    private var nashornScriptEngineFactory: NashornScriptEngineFactory? = null

    private var markup: String? = null

    @Throws(IOException::class)
    private fun readStream(resource: Resource): String {
        val bytes = ByteArray(resource.contentLength().toInt())
        var inputStream: InputStream? = null
        try {
            inputStream = resource.getInputStream()
            resource.inputStream!!.read(bytes)
            return String(bytes)
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
    }

    @PostConstruct
    @Throws(Exception::class)
    fun init() {
        val config = GenericObjectPoolConfig()
        config.maxTotal = maxTotal!!
        config.maxIdle = maxIdle!!
        config.minIdle = minIdle!!

        engines = GenericObjectPool(this, config)
        nashornScriptEngineFactory = NashornScriptEngineFactory()

        markup = readStream(resourceLoader.getResource("classpath:/templates/index.html"))
    }

    @Throws(IOException::class, ScriptException::class)
    internal fun feedScript(engine: NashornScriptEngine, script: String) {
        var inputStream: InputStreamReader? = null
        try {
            inputStream = InputStreamReader(resourceLoader.getResource("classpath:/" + script).inputStream)
            engine.eval(inputStream)
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
    }

    override fun create(): Invocable {
        val nashornScriptEngine = nashornScriptEngineFactory!!.getScriptEngine() as NashornScriptEngine
        try {
            feedScript(nashornScriptEngine, "script/polyfill.js")
            feedScript(nashornScriptEngine, "script/render.js")
            feedScript(nashornScriptEngine, "script/server.js")
        } catch (e: ScriptException) {
            throw RuntimeException("unable to load initial scripts", e)
        } catch (e: IOException) {
            throw RuntimeException("unable to load initial scripts", e)
        }

        return nashornScriptEngine
    }

    override fun wrap(invocable: Invocable): PooledObject<Invocable> {
        return DefaultPooledObject(invocable)
    }

    @Throws(Exception::class)
    fun render(modelMap: Map<String, Any>): String {
        val html: String
        var invocable: Invocable? = null
        try {
            invocable = engines!!.borrowObject()
            html = invocable!!.invokeFunction("render", markup, modelMap) as String
        } finally {
            if (invocable != null) {
                engines!!.returnObject(invocable)
            }
        }

        return html
    }
}