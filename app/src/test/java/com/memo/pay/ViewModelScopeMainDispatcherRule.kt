package com.memo.pay

import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executors
import kotlin.coroutines.ContinuationInterceptor

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class ViewModelScopeMainDispatcherRule(
    private val testContext: TestCoroutineContext? = null
) : TestWatcher() {

    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

    override fun starting(description: Description?) {
        super.starting(description)
        if (testContext != null) {
            Dispatchers.setMain(testContext[ContinuationInterceptor] as CoroutineDispatcher)
        } else {
            Dispatchers.setMain(singleThreadExecutor.asCoroutineDispatcher())
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        singleThreadExecutor.shutdownNow()
        Dispatchers.resetMain()
    }
}
