package com.example.myfigma

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfigma.bl.MainSideEffect
import com.example.myfigma.bl.MainState
import com.example.myfigma.bl.MainStore
import com.example.myfigma.demo.cards
import com.example.myfigma.demo.sectionTransactions
import com.example.myfigma.ui.MainPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity(),
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val store = MainStore()

    private var effectJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by store.observeState().collectAsState()
            MainPage(state) { action ->
                store.dispatch(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        effectJob = store.observeSideEffect().onEach { effect ->
            when (effect) {
                MainSideEffect.ShowTodoToast -> {
                    Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()

                }
            }
        }.launchIn(this)
    }

    override fun onPause() {
        super.onPause()
        effectJob?.cancel()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainPage(
        MainState(
            transactions = sectionTransactions,
            cards = cards
        )
    ) {
        //Preview
    }
}



