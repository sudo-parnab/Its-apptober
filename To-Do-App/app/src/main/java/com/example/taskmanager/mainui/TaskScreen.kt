package com.example.taskmanager.mainui

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.Item
import com.example.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun Task(items: List<Item>, modifier: Modifier, viewModel: TaskViewModel){

    if(items.isNotEmpty()){
        LazyColumn(modifier = modifier){
            items(items){
                item->
                TaskCard(item, viewModel)
            }
        }
    }
    else{
        EmptyItems()
    }
}

@Composable
fun TaskCard(item: Item, viewModel: TaskViewModel){

    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){

        Box() {
            Column(modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()) {
                Row {
                    Text(text = "Task : ",
                    fontWeight = FontWeight.Bold
                    )
                    Text(text = item.task)
                }
                Row{
                    Text(text = "Reminder Date : ",
                        fontWeight = FontWeight.Bold
                        )
                    Text(text = item.reminderDate)
                }
                Row{
                    Text(text = "Reminder Time : ",
                        fontWeight = FontWeight.Bold
                        )
                    Text(text = item.reminderTime)
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done Task",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                           val toast =  Toast.makeText(context, "Task Completed", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            coroutineScope.launch {
                                viewModel.deleteItem(item)
                            }
                        }
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Task",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            showDialog = true
                        }
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            coroutineScope.launch {
                                viewModel.deleteItem(item)
                            }
                        }
                )
            }
        }
    }
    if(showDialog){
        UpdateTask(viewModel, item, onDismiss = {showDialog = false} )
    }
}

@Composable
fun EmptyItems(){
    Column( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("No tasks is added!", style = MaterialTheme.typography.headlineSmall)
    }
}