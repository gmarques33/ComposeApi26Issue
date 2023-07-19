package com.hubspot.android.composeapi26bug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    var showButton by mutableStateOf(true)

    setContent {
      MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          val scope = rememberCoroutineScope()
          val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
          )

          LaunchedEffect(modalBottomSheetState.targetValue) {
            showButton = modalBottomSheetState.targetValue == ModalBottomSheetValue.Hidden
          }

          ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
              Column {
                repeat(6) {
                  Text(text = "Testing")
                }
              }
            },
            content = {
              Column {
                Text(text = "The sheet is ${modalBottomSheetState.targetValue}")
                if (showButton) {
                  Button(onClick = {
                    scope.launch {
                      modalBottomSheetState.show()
                    }
                  }) {
                    Text(text = "Click Me")
                  }
                }
              }
            }
          )
        }
      }
    }
  }
}
